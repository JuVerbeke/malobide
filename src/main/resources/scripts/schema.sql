CREATE TABLE meals
(
    id     VARCHAR(255) NOT NULL,
    date   VARCHAR(255) NULL,
    dishes VARCHAR(4095) NULL,
    CONSTRAINT pk_meals PRIMARY KEY (id)
);
