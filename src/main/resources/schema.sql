create TABLE IF NOT EXISTS halls (
   id INTEGER PRIMARY KEY NOT NULL,
   name varchar(255) NOT NULL
);

create TABLE IF NOT EXISTS sessions (
    id INTEGER PRIMARY KEY,
    name TEXT,
    hall_id INTEGER REFERENCES halls(id)
);

create TABLE IF NOT EXISTS seats (
    id SERIAL PRIMARY KEY NOT NULL,
    row INTEGER NOT NULL,
    place INTEGER NOT NULL,
    hall_id INTEGER REFERENCES halls(id)
);

create TABLE IF NOT EXISTS accounts (
   id SERIAL PRIMARY KEY,
   name varchar(255),
   phone varchar(255)
);

create TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    session_id INTEGER REFERENCES sessions(id),
    account_id INTEGER REFERENCES accounts(id),
    hall_id INTEGER REFERENCES halls(id),
    seat_id INTEGER REFERENCES seats(id),
    price INTEGER NOT NULL,
    CONSTRAINT key_unique UNIQUE (session_id, seat_id)
)