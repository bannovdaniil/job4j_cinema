-- liquibase formatted sql
-- changeset liquibase:011
INSERT INTO halls(name, row_count, place_count, description)
VALUES ('Красный', 10, 8, 'Малый зал');
INSERT INTO halls(name, row_count, place_count, description)
VALUES ('Синий', 20, 10, 'Большой зал');
-- rollback delete from halls;
