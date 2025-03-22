CREATE TABLE app_user
(
    id         VARCHAR(255) NOT NULL,
    name       VARCHAR(255) NOT NULL,
    email      VARCHAR(100) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    role       SMALLINT,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_appuser PRIMARY KEY (id)
);

CREATE TABLE barber_shop_services
(
    id          VARCHAR(255) NOT NULL,
    name        VARCHAR(255),
    description VARCHAR(255),
    price       INTEGER,
    CONSTRAINT pk_barber_shop_services PRIMARY KEY (id)
);

CREATE TABLE bookings
(
    id              VARCHAR(255) NOT NULL,
    app_user_id     VARCHAR(255) NOT NULL,
    servicebshop_id VARCHAR(255) NOT NULL,
    working_day_id  VARCHAR(255) NOT NULL,
    time            time WITHOUT TIME ZONE NOT NULL,
    date            date         NOT NULL,
    is_done         BOOLEAN      NOT NULL,
    bookings        VARCHAR(255),
    CONSTRAINT pk_bookings PRIMARY KEY (id)
);

CREATE TABLE shifts
(
    id             VARCHAR(255) NOT NULL,
    start_time     time WITHOUT TIME ZONE NOT NULL,
    end_time       time WITHOUT TIME ZONE NOT NULL,
    working_day_id VARCHAR(255),
    CONSTRAINT pk_shifts PRIMARY KEY (id)
);

CREATE TABLE working_days
(
    id          VARCHAR(255) NOT NULL,
    day_of_week SMALLINT     NOT NULL,
    is_open     BOOLEAN      NOT NULL,
    CONSTRAINT pk_working_days PRIMARY KEY (id)
);

ALTER TABLE app_user
    ADD CONSTRAINT uc_appuser_email UNIQUE (email);

ALTER TABLE working_days
    ADD CONSTRAINT uc_working_days_dayofweek UNIQUE (day_of_week);

ALTER TABLE bookings
    ADD CONSTRAINT FK_BOOKINGS_ON_APPUSER FOREIGN KEY (app_user_id) REFERENCES app_user (id);

ALTER TABLE bookings
    ADD CONSTRAINT FK_BOOKINGS_ON_BOOKINGS FOREIGN KEY (bookings) REFERENCES app_user (id);

ALTER TABLE bookings
    ADD CONSTRAINT FK_BOOKINGS_ON_SERVICEBSHOP FOREIGN KEY (servicebshop_id) REFERENCES barber_shop_services (id);

ALTER TABLE bookings
    ADD CONSTRAINT FK_BOOKINGS_ON_WORKINGDAY FOREIGN KEY (working_day_id) REFERENCES working_days (id);

ALTER TABLE shifts
    ADD CONSTRAINT FK_SHIFTS_ON_WORKINGDAY FOREIGN KEY (working_day_id) REFERENCES working_days (id);