use chrono::NaiveDateTime;

#[derive(Queryable)]
pub struct Item {
    id: i32,
    title: String,
    icon: String,
    url: String,
    time_added: NaiveDateTime
}

#[derive(Queryable)]
pub struct Stock {
    id: i32,
    item_id: i32,
    quantity: i32
}
