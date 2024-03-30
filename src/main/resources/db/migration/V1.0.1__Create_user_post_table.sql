CREATE SCHEMA IF NOT EXISTS shareapp;
CREATE TABLE IF NOT EXISTS shareapp.user_post (
                           post_id          SERIAL PRIMARY KEY,
                           user_id          INTEGER,
                           post_file        VARCHAR(255),
                           post_title       VARCHAR(255),
                           post_thumbnail   VARCHAR(255),
                           post_description TEXT,
                           post_date        DATE,
                           post_views       INTEGER,
                           post_likes       INTEGER,
                           post_comments    TEXT
);