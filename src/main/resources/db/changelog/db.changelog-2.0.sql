--liquibase formatted sql

--changeset EGO-R:1
alter table users
    add column created_at timestamp;

alter table users
    add column modified_at timestamp;

alter table users
    add column created_by varchar(32);

alter table users
    add column modified_by varchar(32);