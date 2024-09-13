--liquibase formatted sql

--changeset EGO-R:1
ALTER TABLE users_aud
DROP CONSTRAINT users_aud_username_key;

--changeset EGO-R:2
ALTER TABLE users_aud
ALTER COLUMN username DROP NOT NULL ;
