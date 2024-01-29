-- liquibase formatted sql
-- changeset liquibase:003
CREATE TABLE genres
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL
);
-- rollback DROP TABLE genres;
