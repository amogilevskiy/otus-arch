CREATE SEQUENCE hibernate_sequence start 1 increment 1;

CREATE TABLE IF NOT EXISTS users
(
    id        INTEGER PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email     VARCHAR(255) NOT NULL
);