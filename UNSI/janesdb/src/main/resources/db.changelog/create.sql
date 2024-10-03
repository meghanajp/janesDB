--liquibase formatted sql

--changeset meghanajp-wf:8 dbms:postgresql
CREATE TABLE IF NOT EXISTS janesDbInfo(
    uuid UUID NOT NULL PRIMARY KEY,
    country CHAR(40) NOT NULL ,
    shipLength DOUBLE PRECISION NOT NULL ,
    shipBeam DOUBLE PRECISION NOT NULL,
    vesselClass VARCHAR(255) NOT NULL
);
