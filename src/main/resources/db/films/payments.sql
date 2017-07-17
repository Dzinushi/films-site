CREATE SEQUENCE payments_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE TABLE payments(
  id BIGINT PRIMARY KEY DEFAULT nextval('payments_id_seq'::regclass),
  basket_id BIGINT,
  count INT,
  time TIMESTAMP
);

ALTER TABLE payments ADD FOREIGN KEY(basket_id) REFERENCES baskets(id);