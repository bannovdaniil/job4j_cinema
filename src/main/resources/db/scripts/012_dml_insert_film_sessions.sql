-- liquibase formatted sql
-- changeset liquibase:011
INSERT INTO film_sessions(film_id, halls_id, start_time, end_time, price)VALUES (1, 1, '2024-02-21 12:30', '2024-02-21 14:00', 350);
INSERT INTO film_sessions(film_id, halls_id, start_time, end_time, price)VALUES (2, 1, '2024-02-21 18:00', '2024-02-21 20:00', 320);
INSERT INTO film_sessions(film_id, halls_id, start_time, end_time, price)VALUES (3, 1, '2024-02-21 21:00', '2024-02-21 23:00', 340);
INSERT INTO film_sessions(film_id, halls_id, start_time, end_time, price)VALUES (4, 1, '2024-02-22 12:00', '2024-02-22 14:00', 330);
INSERT INTO film_sessions(film_id, halls_id, start_time, end_time, price)VALUES (5, 1, '2024-02-22 18:00', '2024-02-22 20:00', 300);
INSERT INTO film_sessions(film_id, halls_id, start_time, end_time, price)VALUES (6, 1, '2024-02-22 21:00', '2024-02-22 23:00', 350);

INSERT INTO film_sessions(film_id, halls_id, start_time, end_time, price)VALUES (1, 2, '2024-02-21 12:00', '2024-02-21 14:00', 300);
INSERT INTO film_sessions(film_id, halls_id, start_time, end_time, price)VALUES (2, 2, '2024-02-21 18:00', '2024-02-21 20:00', 300);
INSERT INTO film_sessions(film_id, halls_id, start_time, end_time, price)VALUES (3, 2, '2024-02-21 21:00', '2024-02-21 23:00', 300);
INSERT INTO film_sessions(film_id, halls_id, start_time, end_time, price)VALUES (4, 2, '2024-02-22 12:00', '2024-02-22 14:00', 300);
INSERT INTO film_sessions(film_id, halls_id, start_time, end_time, price)VALUES (5, 2, '2024-02-22 18:00', '2024-02-22 20:00', 300);
INSERT INTO film_sessions(film_id, halls_id, start_time, end_time, price)VALUES (6, 2, '2024-02-22 21:00', '2024-02-22 23:00', 300);

-- rollback delete from halls;
