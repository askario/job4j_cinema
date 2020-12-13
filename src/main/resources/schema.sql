create TABLE IF NOT EXISTS hall (
   id SERIAL PRIMARY KEY,
   name TEXT,
   places TEXT,
   version integer
);

create TABLE IF NOT EXISTS account (
   id SERIAL PRIMARY KEY,
   name varchar(255),
   phone varchar(255)
);