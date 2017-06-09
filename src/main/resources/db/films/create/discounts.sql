CREATE SEQUENCE discounts_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE TABLE discounts(
  id BIGINT PRIMARY KEY DEFAULT nextval('discounts_id_seq'::regclass),
  code VARCHAR(16)
);