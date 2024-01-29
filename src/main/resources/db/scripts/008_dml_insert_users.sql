-- liquibase formatted sql
-- changeset liquibase:008
INSERT INTO users(full_name, email, password)
VALUES ('Иван', 'ivan@a.ru', '123');
INSERT INTO users(full_name, email, password)
VALUES ('Василий', 'vas@a.ru', '123');
INSERT INTO users(full_name, email, password)
VALUES ('Олег', 'o@mail.ru', '123');
INSERT INTO users(full_name, email, password)
VALUES ('Игорь', 'i@mail.ru', '123')
-- rollback delete from users;
