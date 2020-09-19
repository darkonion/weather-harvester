CREATE TABLE IF NOT EXISTS BASIC_MEASUREMENT (
    id SERIAL PRIMARY KEY,
    date TIMESTAMP NOT NULL,
    temperature NUMERIC (10, 2),
    pressure NUMERIC (10, 2),
    humidity NUMERIC (10, 2),
    lux NUMERIC (10, 2)
);