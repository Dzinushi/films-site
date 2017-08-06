-- GENERATE DATE FOR TABLE 'orders'

-- insert into 'discount'
Create or replace function insert_order(number integer) returns void as $$
declare
	o_basket_id BIGINT := 1;
  o_film_id BIGINT;
  o_discount_id BIGINT;
  o_priceByDiscount INT;
  o_mark BOOLEAN;

  baskets_count INT:= 0;
  films_count INT := 0;
  discounts_count INT:= 0;

  films_basket_count INT := 0;
  discount_for_film INT := 0;
  discount_value REAL;
  check_basket_film BOOLEAN;
  have_basket_film BOOLEAN;
  film_price INT;
begin

	-- drop orders
  DROP TABLE IF EXISTS orders CASCADE;
  DROP SEQUENCE IF EXISTS orders_id_seq CASCADE;

	-- create orders
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

  -- get baskets count
  select count(id) into baskets_count from baskets;

  -- get films count
  select count(id) into films_count from films;

  -- get discounts count
  select count(id) into discounts_count from discounts;

	for i in 1..number loop

    if i % 10000 = 0 then
			raise notice 'orders_insert: %', i;
		end if;

    -- set random baskets
    if films_basket_count = 0 then
      o_basket_id := trunc(random() * baskets_count) + 1;

      -- create films count from 1 to 50 for basket_id
      films_basket_count := trunc(random() * 50) + 1;
      films_basket_count := films_basket_count % films_count;
    else
      films_basket_count := films_basket_count - 1;
    end if;

    -- set film id
    check_basket_film := true;
    while check_basket_film loop
      select trunc(random() * films_count) + 1 into o_film_id from films LIMIT 1;
      select EXISTS (select 1 into have_basket_film from orders where basket_id = o_basket_id and film_id = o_film_id);
      if have_basket_film = false then
        check_basket_film := false;
      end if;
    end loop;

    -- set discount chance as 10% to check discount
    discount_for_film := trunc(random() * 10);

    -- set discount
    if discount_for_film = 0 then
      select
        trunc(random() * discounts_count) + 1::int as id into o_discount_id
      from
        discounts as d
        INNER JOIN user_discounts as ud on ud.discount_id = d.id
      where
        ud.used = false
      LIMIT 1;
    end if;

    -- set price_by_discount
    select price into film_price from films where id = o_film_id;

    if o_discount_id > 0 then
      select value into discount_value from discounts where id = o_discount_id;
      o_priceByDiscount := film_price - trunc(film_price * discount_value);
    else
      o_priceByDiscount := film_price;
    end if;

    -- set mark
    o_mark := trunc(random() * 2);

    -- insert orders
    insert into orders (basket_id, film_id, discount_id, price_by_discount, mark, time)
			values(o_basket_id, o_film_id, o_discount_id, o_priceByDiscount, o_mark, clock_timestamp());
	end loop;
end;
$$ language plpgsql;


select insert_order(1000000);
select count(id) from orders;
