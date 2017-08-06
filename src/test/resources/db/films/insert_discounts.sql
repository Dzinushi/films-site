-- GENERATE DATE FOR TABLE 'discount'

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


-- insert into 'discount'
Create or replace function insert_discount(number integer) returns void as $$
declare
	code varchar(16) := '';
	value real;
begin

	-- drop discount
	DROP TABLE IF EXISTS discounts CASCADE;
	DROP SEQUENCE IF EXISTS discounts_id_seq CASCADE;

	-- create discount
	CREATE SEQUENCE discounts_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
	CREATE TABLE discounts(
	  id BIGINT PRIMARY KEY DEFAULT nextval('discounts_id_seq'::regclass),
	  code VARCHAR(16) UNIQUE,
	  value REAL,
	  time TIMESTAMP
	);

	-- insert discounts
	for i in 1..number loop
		if i % 10000 = 0 then
			raise notice 'discounts_insert: %', i;
		end if;
		select random_string(16) into code;      -- set code
		value := (trunc(random()*45) + 5) / 100; -- set value
		insert into discounts (code, value, time)
			values(code, value, clock_timestamp());
	end loop;
end;
$$ language plpgsql;


select insert_discount(10000000);
select count(id) from discounts;
