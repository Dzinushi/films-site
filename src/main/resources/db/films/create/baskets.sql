CREATE SEQUENCE baskets_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE TABLE baskets(
  id BIGINT PRIMARY KEY DEFAULT nextval('baskets_id_seq'::regclass),
  user_id BIGINT,
  film_id BIGINT,
  discount_id BIGINT,
  UNIQUE (user_id, film_id)
);

ALTER TABLE baskets ADD FOREIGN KEY(user_id) REFERENCES users(id);
ALTER TABLE baskets ADD FOREIGN KEY(film_id) REFERENCES films(id);
ALTER TABLE baskets ADD FOREIGN KEY(discount_id) REFERENCES discounts(id);