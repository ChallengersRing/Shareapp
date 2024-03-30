CREATE SCHEMA IF NOT EXISTS shareapp;
CREATE TABLE IF NOT EXISTS shareapp.user_post
(
    id          SERIAL PRIMARY KEY,
    user_id     INTEGER,
    ext_id      uuid                 DEFAULT gen_random_uuid(),
    version     BIGINT      NOT NULL DEFAULT 0,
    file        VARCHAR(255),
    title       VARCHAR(255),
    thumbnail   VARCHAR(255),
    description TEXT,
    views       INTEGER              DEFAULT 0,
    likes       INTEGER              DEFAULT 0,
    comments    TEXT,
    deleted     BOOLEAN              DEFAULT FALSE,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT current_timestamp,
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT current_timestamp
);