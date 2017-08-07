CREATE SEQUENCE users_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE TABLE users (
  id BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('users_id_seq'::regclass),
  login VARCHAR(16) UNIQUE NOT NULL,
  password VARCHAR(64) NOT NULL,
  enabled SMALLINT NOT NULL DEFAULT 1,
  time TIMESTAMP
);


CREATE SEQUENCE user_roles_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE TABLE user_roles (
  id    BIGINT NOT NULL DEFAULT nextval('user_roles_id_seq'::regclass),
  login VARCHAR(16) NOT NULL REFERENCES users(login) ON DELETE SET NULL,
  role  VARCHAR(45) NOT NULL,
  time TIMESTAMP
);

CREATE SEQUENCE films_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE TABLE films (
  id BIGINT PRIMARY KEY DEFAULT nextval('films_id_seq'::regclass),
  name VARCHAR(128),
  genre VARCHAR(128),
  duration SMALLINT,
  price INT,
  image VARCHAR(256) UNIQUE,
  time TIMESTAMP
);


CREATE SEQUENCE discounts_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE TABLE discounts(
  id BIGINT PRIMARY KEY DEFAULT nextval('discounts_id_seq'::regclass),
  code VARCHAR(16) UNIQUE,
  value REAL,
  time TIMESTAMP
);


CREATE SEQUENCE baskets_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE TABLE baskets (
  id BIGINT PRIMARY KEY DEFAULT nextval('baskets_id_seq' :: REGCLASS),
  user_id BIGINT UNIQUE REFERENCES users(id) ON DELETE SET NULL,
  time TIMESTAMP
);

CREATE SEQUENCE orders_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE TABLE orders(
  id BIGINT PRIMARY KEY DEFAULT nextval('orders_id_seq'::regclass),
  basket_id BIGINT REFERENCES baskets(id) ON DELETE SET NULL,
  film_id BIGINT REFERENCES films(id) ON DELETE SET NULL,
  discount_id BIGINT REFERENCES discounts(id) ON DELETE SET NULL,
  price_by_discount INT,
  mark BOOLEAN,
  time TIMESTAMP,
  UNIQUE (basket_id, film_id)
);


CREATE SEQUENCE user_discounts_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE TABLE user_discounts(
  id BIGINT PRIMARY KEY DEFAULT nextval('user_discounts_id_seq'::regclass),
  user_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
  discount_id BIGINT REFERENCES discounts(id) ON DELETE SET NULL,
  used BOOLEAN,
  time TIMESTAMP
);


CREATE SEQUENCE payments_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE TABLE payments(
  id BIGINT PRIMARY KEY DEFAULT nextval('payments_id_seq'::regclass),
  user_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
  film_id BIGINT REFERENCES films(id) ON DELETE SET NULL,
  discount_id BIGINT REFERENCES discounts(id) ON DELETE SET NULL,
  total_price INT,
  time TIMESTAMP
);


CREATE SEQUENCE data_validates_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE TABLE data_validates(
  id BIGINT PRIMARY KEY DEFAULT nextval('data_validates_id_seq'::regclass),
  field text,
  store text,
  time TIMESTAMP,
  unique (field, store)
);