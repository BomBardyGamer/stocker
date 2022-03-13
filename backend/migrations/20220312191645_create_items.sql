-- Create the items table. Stocker uses this to keep track of all the
-- items it knows about.
CREATE TABLE items (
    id SERIAL PRIMARY KEY,
    gtin INTEGER NOT NULL,
    title TEXT NOT NULL,
    icon TEXT NOT NULL,
    url TEXT NOT NULL,
    time_added TIMESTAMP NOT NULL
);
