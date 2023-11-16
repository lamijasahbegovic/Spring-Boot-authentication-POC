-- liquibase formatted sql
-- changeSet create-account-table.sql stripComments:true splitStatements:true tag: create_account_table

CREATE TABLE "accounts" (
     "id" BIGSERIAL NOT NULL,
     "email" TEXT NOT NULL,
     "password" TEXT NOT NULL,
     "username" TEXT,
     "date_of_birth" DATE,
     "role" VARCHAR(30) REFERENCES "roles"(value),
     "created_at" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
     "updated_at" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
     CONSTRAINT "account_pkey" PRIMARY KEY ("id")
);

CREATE UNIQUE INDEX "account_email_idx" ON "accounts"("email");

-- rollback DROP TABLE "accounts";
