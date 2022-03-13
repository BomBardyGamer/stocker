-- Create the stocks table. Stocker uses this to keep track of the
-- actual inventory, i.e. what we currently have.
CREATE TABLE stock (
    id SERIAL PRIMARY KEY,
    item_id SERIAL REFERENCES items(id),
    quantity INTEGER NOT NULL
);
