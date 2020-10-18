CREATE TABLE IF NOT EXISTS BASIC_MEASUREMENT (
    id SERIAL PRIMARY KEY,
    date TIMESTAMP NOT NULL,
    temperature NUMERIC (10, 2),
    pressure NUMERIC (10, 2),
    humidity NUMERIC (10, 2),
    lux NUMERIC (10, 2)
);

CREATE TABLE IF NOT EXISTS AIR_PURITY_MEASUREMENT (
    id SERIAL PRIMARY KEY,
    date TIMESTAMP NOT NULL,
    pm1 NUMERIC (10, 2),
    pm25 NUMERIC (10, 2),
    pm10 NUMERIC (10, 2)
);

CREATE TABLE IF NOT EXISTS CRON (
    id SERIAL PRIMARY KEY,
    basic_cron VARCHAR(50),
    air_cron VARCHAR(50)
);