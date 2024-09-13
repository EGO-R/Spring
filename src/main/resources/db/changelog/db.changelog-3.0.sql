--liquibase formatted sql

--changeset EGO-R:1
ALTER TABLE users
ADD COLUMN image VARCHAR(64);

--changeset EGO-R:2
ALTER TABLE users_aud
ADD COLUMN image VARCHAR(64);
