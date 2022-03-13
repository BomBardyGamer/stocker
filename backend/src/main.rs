mod api;
mod config;
mod models;

use actix_web::{App, HttpServer};
use actix_web::middleware::Logger;
use actix_web::web::Data;
use crate::config::Config;

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    let config = Config::resolve();
    let pool = config.database
        .create_pool()
        .await
        .unwrap();
    let pool_data = Data::new(pool);
    let create_app = move || App::new()
        .app_data(pool_data.clone())
        .wrap(Logger::default())
        .service(api::create_item)
        .service(api::get_item);
    HttpServer::new(create_app)
        .bind(config.web.bind_address())?
        .run()
        .await
}
