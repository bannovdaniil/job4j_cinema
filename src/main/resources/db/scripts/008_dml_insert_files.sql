-- liquibase formatted sql
-- changeset liquibase:008
INSERT INTO files(name, path)
VALUES ('dogman2023.jpg','files\1-dogman2023.jpg'); -- 1
INSERT INTO files(name, path)
VALUES ('beekeeper2024.jpg','files\2-beekeeper2024.jpg'); -- 2
INSERT INTO files(name, path)
VALUES ('master2023.jpg','files\3-master2023.jpg'); -- 3
INSERT INTO files(name, path)
VALUES ('holop2-2023.jpg','files\4-holop2-2023.jpg'); -- 4
INSERT INTO files(name, path)
VALUES ('ivanvse2023.jpg','files\5-ivanvse2023.jpg'); -- 5
INSERT INTO files(name, path)
VALUES ('stalker2023.jpg','files\6-stalker2023.jpg'); -- 6
-- rollback delete from files;
