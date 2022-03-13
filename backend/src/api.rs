use actix_web::{get, HttpResponse, post, Responder, ResponseError};
use actix_web::http::StatusCode;
use actix_web::web::{Data, Json, Query};
use chrono::Utc;
use actix_web::error::InternalError;
use serde_derive::Deserialize;
use sqlx::{Arguments, Connection, Encode, IntoArguments, PgPool, Postgres, query_with, Row, Type};
use sqlx::database::HasArguments;
use sqlx::postgres::PgArguments;
use crate::models::Item;

const CREATE_ITEM_QUERY: &str = "INSERT INTO items (gtin, title, icon, url, time_added) \
    VALUES ($1, $2, $3, $4, $5)";
const GET_ITEM_QUERY: &str = "SELECT title, icon, url, time_added FROM items WHERE gtin=$1";

#[derive(Deserialize)]
pub struct NewItem {
    gtin: i32,
    title: String,
    icon: String,
    url: String
}

#[post("/items/create")]
pub async fn create_item(item: Json<NewItem>, pool: Data<PgPool>) -> impl Responder {
    let now = Utc::now().naive_utc();
    let mut connection = match pool.acquire().await {
        Ok(connection) => connection,
        Err(error) => return InternalError::new(error, StatusCode::INTERNAL_SERVER_ERROR).error_response()
    };
    let result = connection.transaction(|connection| Box::pin(async move {
        let arguments = ArgumentBuilder::new()
            .add(item.gtin)
            .add(item.title.clone())
            .add(item.icon.clone())
            .add(item.url.clone())
            .add(now);
        query_with(CREATE_ITEM_QUERY, arguments)
            .execute(connection)
            .await
    })).await;
    match result {
        Ok(_) => HttpResponse::Ok().finish(),
        Err(error) => InternalError::new(error, StatusCode::INTERNAL_SERVER_ERROR).error_response()
    }
}

#[derive(Deserialize)]
pub struct GetParameters {
    gtin: i32
}

#[get("/items")]
pub async fn get_item(parameters: Query<GetParameters>, pool: Data<PgPool>) -> impl Responder {
    let mut connection = match pool.acquire().await {
        Ok(connection) => connection,
        Err(error) => return InternalError::new(error, StatusCode::INTERNAL_SERVER_ERROR).error_response()
    };
    let gtin = parameters.gtin;
    let result = connection.transaction(|connection| Box::pin(async move {
        query_with(GET_ITEM_QUERY, ArgumentBuilder::new().add(gtin))
            .fetch_optional(connection)
            .await
    })).await;
    let item = match result {
        Ok(Some(row)) => Item {
            gtin,
            title: row.get(0),
            icon: row.get(1),
            url: row.get(2),
            time_added: row.get(3)
        },
        Ok(None) => return HttpResponse::NoContent().finish(),
        Err(error) => return InternalError::new(error, StatusCode::INTERNAL_SERVER_ERROR).error_response()
    };
    HttpResponse::Ok().body(serde_json::to_string(&item).unwrap())
}

struct ArgumentBuilder(PgArguments);

impl ArgumentBuilder {
    fn new() -> Self {
        Self(PgArguments::default())
    }

    fn add<'q, T: Encode<'q, Postgres> + Type<Postgres> + Send + 'q>(mut self, argument: T) -> Self {
        self.0.add(argument);
        self
    }
}

impl<'q> IntoArguments<'q, Postgres> for ArgumentBuilder {
    fn into_arguments(self) -> <Postgres as HasArguments<'q>>::Arguments {
        self.0
    }
}
