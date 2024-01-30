-- liquibase formatted sql
-- changeset liquibase:011
INSERT INTO film_sessions(film_id, halls_id, start_time, end_time, price)
VALUES (1, 1, DATE '2023-08-31', DATE '2024-01-25', 350);
INSERT INTO film_sessions(film_id, halls_id, start_time, end_time, price)
VALUES (2, 1, DATE '2024-01-08', DATE '2024-04-15', 320);
INSERT INTO film_sessions(film_id, halls_id, start_time, end_time, price)
VALUES (3, 1, DATE '2024-01-25', DATE '2024-05-11', 340);
INSERT INTO film_sessions(film_id, halls_id, start_time, end_time, price)
VALUES (4, 1, DATE '2024-01-01', DATE '2025-03-23', 330);
INSERT INTO film_sessions(film_id, halls_id, start_time, end_time, price)
VALUES (5, 1, DATE '2023-12-31', DATE '2024-02-11', 300);
INSERT INTO film_sessions(film_id, halls_id, start_time, end_time, price)
VALUES (6, 1, DATE '2023-12-10', DATE '2025-02-21', 350);

INSERT INTO film_sessions(film_id, halls_id, start_time, end_time, price)
VALUES (1, 2, DATE '2024-01-25', DATE '2024-05-01', 300);
INSERT INTO film_sessions(film_id, halls_id, start_time, end_time, price)
VALUES (2, 2, DATE '2024-04-15', DATE '2024-07-12', 300);
INSERT INTO film_sessions(film_id, halls_id, start_time, end_time, price)
VALUES (3, 2, DATE '2024-05-11', DATE '2024-08-17', 300);
INSERT INTO film_sessions(film_id, halls_id, start_time, end_time, price)
VALUES (4, 2, DATE '2025-03-23', DATE '2024-09-21', 300);
INSERT INTO film_sessions(film_id, halls_id, start_time, end_time, price)
VALUES (5, 2, DATE '2024-02-11', DATE '2024-10-14', 300);
INSERT INTO film_sessions(film_id, halls_id, start_time, end_time, price)
VALUES (6, 2, DATE '2025-02-21', DATE '2024-11-10', 300);

-- rollback delete from halls;
