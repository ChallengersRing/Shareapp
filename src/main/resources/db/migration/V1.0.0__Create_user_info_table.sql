CREATE SCHEMA IF NOT EXISTS shareapp;
CREATE TABLE IF NOT EXISTS shareapp.user_info
(
    id            SERIAL PRIMARY KEY,
    ext_id        uuid                 DEFAULT gen_random_uuid(),
    avatar        VARCHAR(255),
    firstname     VARCHAR(50) NOT NULL,
    lastname      VARCHAR(50) NOT NULL,
    gender        VARCHAR(10),
    email         VARCHAR(255) UNIQUE,
    phone         VARCHAR(20),
    password      VARCHAR(255),
    date_of_birth DATE,
    deleted       BOOLEAN              DEFAULT FALSE,
    created_at    TIMESTAMP   NOT NULL DEFAULT current_timestamp,
    updated_at    TIMESTAMP   NOT NULL DEFAULT current_timestamp
);
