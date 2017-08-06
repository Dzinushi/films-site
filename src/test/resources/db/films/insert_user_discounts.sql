-- GENERATE DATE FOR TABLE 'user_discounts'

-- function for generate strings
Create or replace function random_string(length integer) returns text as
$$
declare
  chars text[] := '{0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z}';
  result text := '';
  i integer := 0;
begin
  --raise notice 'rand_length: %', rand_length;
  if length < 0 then
    raise exception 'Given length cannot be less than 0';
  end if;
  for i in 1..length loop
    result := result || chars[1+random()*(array_length(chars, 1)-1)];
  end loop;
  return result;
end;
$$ language plpgsql;


-- insert into 'user_discounts'
Create or replace function insert_user_discount(number integer) returns void as $$
declare
	ud_user_id BIGINT := 0;
	ud_discount_id BIGINT := 0;
	ud_used BOOLEAN := false;
	used_rand INT := 0;

  user_count INT;
  discount_count INT;
  next BOOLEAN;
begin
	-- drop user_discounts
	DROP TABLE IF EXISTS user_discounts CASCADE;
	DROP SEQUENCE IF EXISTS user_discounts_id_seq CASCADE;

	-- create user_discounts
	CREATE SEQUENCE user_discounts_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
	CREATE TABLE user_discounts(
	  id BIGINT PRIMARY KEY DEFAULT nextval('user_discounts_id_seq'::regclass),
	  user_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
	  discount_id BIGINT REFERENCES discounts(id) ON DELETE SET NULL,
	  used BOOLEAN,
	  time TIMESTAMP
	);

  select count(id) into user_count from users;
  select count(id) into discount_count from discounts;

  if number > discount_count then
    raise exception 'user_discounts rows more than discounts rows: % > %', number, discount_count;
  end if;

	-- insert user_discounts
	for i in 1..number loop

		if i % 10000 = 0 then
			raise notice 'user_discounts_insert: %', i;
		end if;

		-- set user_id
		ud_user_id := trunc(random() * user_count) + 1;

    -- set discount_id
  	select id into ud_discount_id from discounts where id = i;

		-- set used
		used_rand := trunc(random() * 100) + 1;
		if used_rand > 20 then
			ud_used := FALSE;
		else
			ud_used := TRUE;
		end if;

		-- insert data
		insert into user_discounts (user_id, discount_id, used, time)
			values(ud_user_id, ud_discount_id, ud_used, clock_timestamp());
	end loop;
end;
$$ language plpgsql;

select insert_user_discount(1000000);
select count(id) from user_discounts;