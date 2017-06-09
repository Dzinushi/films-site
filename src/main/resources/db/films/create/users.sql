CREATE SEQUENCE users_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE TABLE users (
  id BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('users_id_seq'::regclass),
  login VARCHAR(16) UNIQUE NOT NULL,
  password VARCHAR(64) NOT NULL,
  enabled SMALLINT NOT NULL DEFAULT 1
);