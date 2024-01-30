-- liquibase formatted sql
-- changeset liquibase:009
INSERT INTO genres(name)
VALUES ('Комедия'); -- 1
INSERT INTO genres(name)
VALUES ('фантастика'); -- 2
INSERT INTO genres(name)
VALUES ('Драма'); -- 3
INSERT INTO genres(name)
VALUES ('Боевик'); -- 4
-- rollback delete from genres;
