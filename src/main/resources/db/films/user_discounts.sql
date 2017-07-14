CREATE SEQUENCE user_discounts_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE TABLE user_discounts(
  id BIGINT PRIMARY KEY DEFAULT nextval('user_discounts_id_seq'::regclass),
  user_id BIGINT,
  discount_id BIGINT,
  used BOOLEAN
);

ALTER TABLE user_discounts ADD FOREIGN KEY(user_id) REFERENCES users(id);
ALTER TABLE user_discounts ADD FOREIGN KEY(discount_id) REFERENCES discounts(id);