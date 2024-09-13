--liquibase formatted sql

--changeset EGO-R:1
CREATE TABLE IF NOT EXISTS company
(
    id SERIAL PRIMARY KEY ,
    name VARCHAR(64) NOT NULL UNIQUE
    );
--rollback DROP TABLE company

--changeset EGO-R:2
CREATE TABLE IF NOT EXISTS company_locales
(
    company_id INT REFERENCES company (id) on delete cascade ,
    lang VARCHAR(2),
    description VARCHAR(255) NOT NULL ,
    PRIMARY KEY (company_id, lang)
    );

--changeset EGO-R:3
CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL PRIMARY KEY ,
    username VARCHAR(64) NOT NULL UNIQUE ,
    birth_date DATE,
    firstname VARCHAR(64),
    lastname VARCHAR(64),
    role VARCHAR(32),
    company_id INT REFERENCES company (id)
    );

--changeset EGO-R:4
CREATE TABLE IF NOT EXISTS payment
(
    id BIGSERIAL PRIMARY KEY ,
    amount INT NOT NULL ,
    receiver_id BIGINT NOT NULL REFERENCES users (id) on delete cascade
    );

--changeset EGO-R:5
CREATE TABLE IF NOT EXISTS chat
(
    id BIGSERIAL PRIMARY KEY ,
    name VARCHAR(64) NOT NULL UNIQUE
    );

--changeset EGO-R:6
CREATE TABLE IF NOT EXISTS users_chat
(
    id BIGSERIAL PRIMARY KEY ,
    user_id BIGINT NOT NULL REFERENCES users (id) on delete CASCADE ,
    chat_id BIGINT NOT NULL REFERENCES chat (id) on DELETE cascade ,
    UNIQUE (user_id, chat_id)
    );