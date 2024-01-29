-- liquibase formatted sql
-- changeset liquibase:001
CREATE TABLE files
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    path VARCHAR NOT NULL UNIQUE
);
-- rollback DROP TABLE files;
