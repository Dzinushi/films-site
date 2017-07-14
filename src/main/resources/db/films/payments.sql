CREATE SEQUENCE payments_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE TABLE payments(
  id BIGINT PRIMARY KEY DEFAULT nextval('payments_id_seq'::regclass),
  user_id BIGINT,
  film_id BIGINT,
  discount_id BIGINT,
  count INT,
  time TIMESTAMP
);

ALTER TABLE payments ADD FOREIGN KEY(user_id) REFERENCES users(id);
ALTER TABLE payments ADD FOREIGN KEY(film_id) REFERENCES films(id);
ALTER TABLE payments ADD FOREIGN KEY(discount_id) REFERENCES discounts(id);