#[macro_use]
extern crate diesel;

mod config;
mod models;

use actix_web::{get, web, App, HttpServer, Responder};
use diesel::PgConnection;
use diesel::r2d2::ConnectionManager;
use r2d2::Pool;
use crate::config::Config;

type DbPool = Pool<ConnectionManager<PgConnection>>;

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    let config = Config::resolve();
    let manager = ConnectionManager::<PgConnection>::new(config.database.url());
    let pool = Pool::builder()
        .build(manager)
        .expect("Failed to create connection pool!");

    let create_app = move || {
        App::new()
            .app_data(pool.clone())
            .route("/hello", web::get().to(|| async { "Hello World!" }))
            .service(greet)
    };
    HttpServer::new(create_app)
        .bind(config.web.bind_address())?
        .run()
        .await
}

#[get("/hello/{name}")]
async fn greet(name: web::Path<String>) -> impl Responder {
    format!("Hello {name}!")
}
