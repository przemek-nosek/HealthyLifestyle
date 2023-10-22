-- liquibase formatted sql


-- changeset przemyslaw.nosek:1697574293000-1
CREATE TABLE measurement
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    uuid            VARCHAR(100),
    weight          DECIMAL,
    calfSize        DECIMAL,
    thighSize       DECIMAL,
    waistSize       DECIMAL,
    chestSize       DECIMAL,
    neckSize        DECIMAL,
    forearmSize     DECIMAL,
    bicepsSize      DECIMAL,
    measurementDate DATE,
    userUuid        VARCHAR(100)
);