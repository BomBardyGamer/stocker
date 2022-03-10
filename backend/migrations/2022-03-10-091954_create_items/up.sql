-- Create the items table. Stocker uses this to keep track of all the
-- items it knows about.
CREATE TABLE items (
    id SERIAL PRIMARY KEY,
    title TEXT,
    icon TEXT,
    url TEXT,
    time_added TIMESTAMP
);
