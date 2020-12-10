CREATE TABLE IF NOT EXISTS hall (
   id SERIAL PRIMARY KEY,
   name TEXT,
   places TEXT
);

CREATE TABLE IF NOT EXISTS account (
   id SERIAL PRIMARY KEY,
   name varchar(255),
   phone varchar(255)
);