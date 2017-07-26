CREATE SEQUENCE user_roles_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE TABLE user_roles (
  id    BIGINT NOT NULL DEFAULT nextval('user_roles_id_seq'::regclass),
  login VARCHAR(45) NOT NULL,
  role  VARCHAR(45) NOT NULL,
  time TIMESTAMP,
  UNIQUE (login, role)
);

ALTER TABLE user_roles ADD FOREIGN KEY(login) REFERENCES users(login);