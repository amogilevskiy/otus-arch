CREATE SEQUENCE hibernate_sequence start 1 increment 1;

CREATE TABLE IF NOT EXISTS orders
(
    id      INTEGER PRIMARY KEY,
    content VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    comment VARCHAR(255)
);