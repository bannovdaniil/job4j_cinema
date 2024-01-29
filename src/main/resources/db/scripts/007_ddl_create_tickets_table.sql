-- liquibase formatted sql
-- changeset liquibase:006
CREATE TABLE tickets
(
    id           SERIAL PRIMARY KEY,
    session_id   INT REFERENCES film_sessions (id) NOT NULL,
    row_number   INT                               NOT NULL,
    place_number INT                               NOT NULL,
    user_id      INT                               NOT NULL,
    UNIQUE (session_id, row_number, place_number)
);
-- rollback DROP TABLE tickets;
