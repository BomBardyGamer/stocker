use serde_derive::Deserialize;
use std::path::Path;

#[derive(Debug, Deserialize)]
pub struct Config {
    pub web: WebConfig,
    pub database: DatabaseConfig,
}

impl Config {
    pub fn resolve() -> Self {
        let contents = std::fs::read_to_string(Path::new("config.toml")).unwrap();
        toml::from_str(contents.as_str()).unwrap()
    }
}

#[derive(Debug, Deserialize)]
pub struct WebConfig {
    hostname: Option<String>,
    port: Option<u16>
}

impl WebConfig {
    pub fn bind_address(&self) -> (&str, u16) {
        (self.hostname.as_ref().map(String::as_str).unwrap_or("127.0.0.1"), self.port.unwrap_or(8080))
    }
}

#[derive(Debug, Deserialize)]
pub struct DatabaseConfig {
    hostname: Option<String>,
    port: Option<u16>,
    name: Option<String>,
    username: Option<String>,
    password: Option<String>
}

impl DatabaseConfig {
    pub fn url(&self) -> String {
        let hostname = self.hostname.as_ref().map(String::as_str).unwrap_or("127.0.0.1");
        let port = self.port.unwrap_or(5432);
        let name = self.name.as_ref().map(String::as_str).unwrap_or("stocker");
        let username = self.username.as_ref().map(String::as_str).unwrap_or("stocker");
        let password = self.password.as_ref().map(String::as_str).unwrap_or("stocker");
        format!("postgres://{}:{}@{}:{}/{}", username, password, hostname, port, name)
    }
}
