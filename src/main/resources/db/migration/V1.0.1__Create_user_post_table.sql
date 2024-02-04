CREATE TABLE IF NOT EXISTS user_post (
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

ALTER TABLE  IF EXISTS user_post OWNER TO postgres;
