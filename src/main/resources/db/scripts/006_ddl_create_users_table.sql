-- liquibase formatted sql
-- changeset liquibase:006
CREATE TABLE users
(
    id        SERIAL PRIMARY KEY,
    full_name VARCHAR        NOT NULL,
    email     VARCHAR UNIQUE NOT NULL,
    password  VARCHAR        NOT NULL
);
-- rollback DROP TABLE users;
