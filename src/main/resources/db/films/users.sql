CREATE SEQUENCE users_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  login VARCHAR(16) UNIQUE,
  password VARCHAR(64)
);