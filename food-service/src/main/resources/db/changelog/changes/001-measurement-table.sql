-- liquibase formatted sql


-- changeset przemyslaw.nosek:1697574293000-1
CREATE TABLE measurement
(
    id               BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    uuid             VARCHAR(100),
    weight           DECIMAL,
    calf_size        DECIMAL,
    thigh_size       DECIMAL,
    waist_size       DECIMAL,
    chest_size       DECIMAL,
    neck_size        DECIMAL,
    forearm_size     DECIMAL,
    biceps_size      DECIMAL,
    measurement_date DATE,
    user_uuid        VARCHAR(100)
);