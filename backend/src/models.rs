use chrono::NaiveDateTime;
use serde_derive::Serialize;

#[derive(Serialize)]
pub struct Item {
    pub gtin: i32,
    pub title: String,
    pub icon: String,
    pub url: String,
    pub time_added: NaiveDateTime
}

#[derive(Serialize)]
pub struct Stock {
    pub id: i32,
    pub item_id: i32,
    pub quantity: i32
}
