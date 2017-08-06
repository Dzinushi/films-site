-- GENERATE DATE FOR TABLE 'payments'

-- insert into 'payments'
Create or replace function insert_payment() returns void as $$
declare
	p_user_id BIGINT;
	p_film_id BIGINT;
	p_discount_id BIGINT;
	p_total_price INT;
  number BIGINT;
begin

	-- drop payments
	DROP TABLE IF EXISTS payments CASCADE;
	DROP SEQUENCE IF EXISTS payments_id_seq CASCADE;

	-- create payments
  CREATE SEQUENCE payments_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
  CREATE TABLE payments(
    id BIGINT PRIMARY KEY DEFAULT nextval('payments_id_seq'::regclass),
    user_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
    film_id BIGINT REFERENCES films(id) ON DELETE SET NULL,
    discount_id BIGINT REFERENCES discounts(id) ON DELETE SET NULL,
    total_price INT,
    time TIMESTAMP
  );

  select count(id) into number from orders where mark = true;

  raise notice 'payments for insert: %', number;

	-- insert payments
	for i in 1..number loop

		if i % 10000 = 0 then
			raise notice 'payments_insert: %', i;
		end if;

    --set user_id, film_id, discount_id
    select
      b.user_id,
      o.film_id,
      o.discount_id,
      o.price_by_discount into p_user_id,  p_film_id, p_discount_id, p_total_price
    from
      orders as o
      INNER JOIN baskets as b on b.id = o.id
    where
      b.user_id = i and o.mark = true;

		-- insert data
    insert into payments (user_id, film_id, discount_id, total_price, time)
			values(p_user_id, p_film_id, p_discount_id, p_total_price, clock_timestamp());
	end loop;
end;
$$ language plpgsql;


select insert_payment();
select count(id) from payments;
