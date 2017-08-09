-- GENERATE DATE FOR TABLE 'baskets'

-- insert into 'baskets'
Create or replace function insert_basket(number integer) returns void as $$
declare
	b_user_id BIGINT;

	user_count INT;
begin

	-- drop baskets
  DROP TABLE IF EXISTS baskets CASCADE;
  DROP SEQUENCE IF EXISTS baskets_id_seq CASCADE;

	-- create baskets
  CREATE SEQUENCE baskets_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
  CREATE TABLE baskets (
    id BIGINT PRIMARY KEY DEFAULT nextval('baskets_id_seq' :: REGCLASS),
    user_id BIGINT UNIQUE REFERENCES users(id) ON DELETE SET NULL,
    time TIMESTAMP
  );

	select count(id) into user_count from users;

	if user_count < number then
		raise exception 'users row less than baskets rows: % < %', user_count, number;
	end if;

	-- insert baskets
	for i in 1..number loop
		if i % 10000 = 0 then
			raise notice 'baskets_insert: %', i;
		end if;
		select id into b_user_id from users where id = i;      -- set user_id
		insert into baskets (user_id, time)
			values(b_user_id, clock_timestamp());
	end loop;
end;
$$ language plpgsql;


select insert_basket(1000000);
select * from baskets limit 10;
