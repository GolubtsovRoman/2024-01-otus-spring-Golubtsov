--
--
-- Roman Golubtsov
-- created 20.07.2024
--

DROP TABLE IF EXISTS
    "employee",
    "personal_info",
    "department",
    "office";



CREATE TABLE "department"
(
    "code"        VARCHAR(16) PRIMARY KEY,
    "name"        VARCHAR(255) NOT NULL UNIQUE,
    "description" VARCHAR(1024),
    "manager_id"  BIGINT
);

CREATE TABLE "office"
(
    "id"          BIGINT PRIMARY KEY,
    "address"     VARCHAR NOT NULL,
    "capacity"    INTEGER NOT NULL,
    "description" VARCHAR(1024)
);

CREATE TABLE "personal_info"
(
    "id"              BIGINT PRIMARY KEY,
    "full_name"       VARCHAR(255) NOT NULL,
    "birthdate"       DATE         NOT NULL,
    "employment_date" DATE         NOT NULL,
    "is_man"          BOOLEAN      NOT NULL
);

CREATE TABLE "employee"
(
    "id"                BIGINT PRIMARY KEY,
    "personal_info_id"  BIGSERIAL    NOT NULL REFERENCES "personal_info" ("id"),
    "job_title"         VARCHAR(255) NOT NULL,
    "manager_id"        BIGINT,
    "department_code"   VARCHAR(16) REFERENCES "department" ("code"),
    "office_id"         BIGINT REFERENCES "office" ("id"),
    "additional_number" INTEGER,
    "account_id"        VARCHAR(64)
);

ALTER TABLE "employee"
    ADD CONSTRAINT fk_employee_manager_id FOREIGN KEY (manager_id) REFERENCES "employee" (id);

ALTER TABLE "department"
    ADD CONSTRAINT fk_department_manager_id FOREIGN KEY (manager_id) REFERENCES "employee" (id);
