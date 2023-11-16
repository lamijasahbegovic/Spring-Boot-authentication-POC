-- liquibase formatted sql
-- changeSet create-roles-enum-table.sql stripComments:true splitStatements:true tag: create_roles_enum_table

CREATE TABLE "roles" (
    "id" SERIAL PRIMARY KEY ,
    "value" VARCHAR(30) NOT NULL UNIQUE,
    "created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO "roles" (value) VALUES ('ROLE_USER'), ('ROLE_ADMIN'), ('ROLE_GUEST');

-- rollback DROP TABLE "roles";
