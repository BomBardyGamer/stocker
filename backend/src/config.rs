use std::path::Path;
use std::time::Duration;
use serde::Deserialize;
use sqlx::Error;
use sqlx::postgres::{PgConnectOptions, PgPool, PgPoolOptions};

#[derive(Deserialize)]
pub struct Config {
    pub web: WebConfig,
    pub database: DatabaseConfig
}

impl Config {
    pub fn resolve() -> Self {
        let contents = std::fs::read_to_string(Path::new("config.toml")).unwrap();
        toml::from_str(contents.as_str()).unwrap()
    }
}

#[derive(Deserialize)]
pub struct WebConfig {
    hostname: Option<String>,
    port: Option<u16>
}

impl WebConfig {
    pub fn bind_address(&self) -> (&str, u16) {
        (self.hostname.as_ref().map(String::as_str).unwrap_or("127.0.0.1"), self.port.unwrap_or(8080))
    }
}

#[derive(Deserialize)]
pub struct DatabaseConfig {
    hostname: Option<String>,
    port: Option<u16>,
    name: Option<String>,
    username: Option<String>,
    password: Option<String>,
    pool: PoolConfig
}

impl DatabaseConfig {
    pub async fn create_pool(&self) -> Result<PgPool, Error> {
        self.pool.options()
            .connect_with(self.options())
            .await
    }

    fn options(&self) -> PgConnectOptions {
        PgConnectOptions::new()
            .host(self.hostname.as_ref().map(String::as_str).unwrap_or("127.0.0.1"))
            .port(self.port.unwrap_or(5432))
            .database(self.name.as_ref().map(String::as_str).unwrap_or("stocker"))
            .username(self.username.as_ref().map(String::as_str).unwrap_or("stocker"))
            .password(self.password.as_ref().map(String::as_str).unwrap_or("stocker"))
    }
}

#[derive(Deserialize)]
pub struct PoolConfig {
    max_size: Option<u32>,
    min_idle: Option<u32>,
    max_lifetime: Option<Duration>,
    idle_timeout: Option<Duration>,
    connection_timeout: Option<Duration>
}

impl PoolConfig {
    fn options(&self) -> PgPoolOptions {
        PgPoolOptions::new()
            .max_connections(self.max_size.unwrap_or(10))
            .min_connections(self.min_idle.unwrap_or(0))
            .max_lifetime(self.max_lifetime.or(Some(Duration::from_secs(30 * 60))))
            .idle_timeout(self.idle_timeout.or(Some(Duration::from_secs(10 * 60))))
            .connect_timeout(self.connection_timeout.unwrap_or(Duration::from_secs(30)))
    }
}
