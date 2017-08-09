-- GENERATE DATE FOR TABLE 'payments'

-- insert into 'payments'
Create or replace function insert_payment() returns void as $$
declare
	p_user_id BIGINT;
	p_film_id BIGINT;
	p_discount_id BIGINT;
	p_total_price INT;
	p_time TIMESTAMP;
	chance_time_update int;
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

	select cast(now() - '1 year'::interval * random() as timestamp) into p_time;
	select count(id) into number from orders;

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
			INNER JOIN baskets as b on b.id = o.basket_id
		where
			o.id = i and o.mark = true;

		if p_user_id > 0 then

			-- p_time
			chance_time_update := trunc(random() * 5);
			if chance_time_update = 0 then
				select cast(now() - '1 minute'::interval * random() as timestamp) into p_time;
			end if;

			-- insert data
			insert into payments (user_id, film_id, discount_id, total_price, time)
				values(p_user_id, p_film_id, p_discount_id, p_total_price, p_time);
		end if;
	end loop;
end;
$$ language plpgsql;


select insert_payment();
select * from payments limit 10;