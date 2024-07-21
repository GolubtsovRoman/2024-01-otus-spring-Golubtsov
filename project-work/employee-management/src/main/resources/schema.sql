--
--
-- Roman Golubtsov
-- created 20.07.2024
--


CREATE TABLE IF NOT EXISTS "department" (
    "code"        VARCHAR(16) PRIMARY KEY,
    "name"        VARCHAR(255) NOT NULL UNIQUE,
    "description" VARCHAR(1024)
);


CREATE TABLE IF NOT EXISTS "office" (
    "id"       BIGSERIAL PRIMARY KEY,
    "address"  VARCHAR NOT NULL,
    "capacity" INTEGER NOT NULL,
    "description" VARCHAR(1024)
);


CREATE TABLE IF NOT EXISTS "employee" (
    "id"               BIGSERIAL PRIMARY KEY,
    "personal_info_id" BIGSERIAL NOT NULL,
    "work_info_id"     BIGSERIAL NOT NULL,
    "account_id"       VARCHAR(64)
);


CREATE TABLE IF NOT EXISTS "personal_info" (
    "id"              BIGSERIAL PRIMARY KEY,
    "full_name"       VARCHAR(255) NOT NULL,
    "bday"            DATE         NOT NULL,
    "employment_date" DATE         NOT NULL,
    "man"             BOOLEAN      NOT NULL
);
ALTER TABLE "employee" ADD FOREIGN KEY("personal_info_id") REFERENCES "personal_info"("id") ON DELETE CASCADE;


CREATE TABLE IF NOT EXISTS "work_info" (
    "id"                BIGSERIAL PRIMARY KEY,
    "job_title"         VARCHAR(255) NOT NULL,
    "head_id"           BIGSERIAL REFERENCES "employee" ("id"),
    "department_code"   VARCHAR(16) REFERENCES "department" ("code"),
    "office_id"         BIGSERIAL REFERENCES "office" ("id"),
    "additional_number" INTEGER
);
ALTER TABLE "employee" ADD FOREIGN KEY("work_info_id") REFERENCES "work_info"("id") ON DELETE CASCADE;
