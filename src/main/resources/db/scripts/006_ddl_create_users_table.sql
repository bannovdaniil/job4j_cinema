-- liquibase formatted sql
-- changeset liquibase:006
CREATE TABLE users
(
    id        SERIAL PRIMARY KEY,
    full_name VARCHAR NOT NULL,
    email     VARCHAR NOT NULL,
    password  VARCHAR NOT NULL,
    UNIQUE (email)
);
-- rollback DROP TABLE users;
