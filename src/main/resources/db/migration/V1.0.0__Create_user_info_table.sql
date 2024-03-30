CREATE SCHEMA IF NOT EXISTS shareapp;
CREATE TABLE IF NOT EXISTS shareapp.user_info (
                           user_id            SERIAL PRIMARY KEY,
                           user_photo         VARCHAR(255),
                           user_fname         VARCHAR(50),
                           user_lname         VARCHAR(50),
                           user_dob           DATE,
                           user_gender        VARCHAR(10),
                           user_email         VARCHAR(255) UNIQUE,
                           user_phone         VARCHAR(20),
                           user_password      VARCHAR(255)
);
