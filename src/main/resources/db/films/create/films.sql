CREATE SEQUENCE films_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE TABLE films (
  id BIGINT PRIMARY KEY DEFAULT nextval('films_id_seq'::regclass),
  name VARCHAR(128),
  genre VARCHAR(128),
  duration TIME,
  price INT,
  image VARCHAR(256) UNIQUE
);