-- GENERATE DATE FOR TABLE 'users'

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


-- insert into 'users'
Create or replace function insert_users(number integer) returns void as $$
declare
	login varchar(16) := '';
	pass varchar(64) := '';
	enabled integer := 0;
begin

	-- drop users
	DROP TABLE IF EXISTS users CASCADE;
	DROP SEQUENCE IF EXISTS users_id_seq CASCADE;

	-- create users
	CREATE SEQUENCE users_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
	CREATE TABLE users (
		id BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('users_id_seq'::regclass),
		login VARCHAR(16) UNIQUE NOT NULL,
		password VARCHAR(64) NOT NULL,
		enabled SMALLINT NOT NULL DEFAULT 1,
		time TIMESTAMP
	);

	-- insert users
	for i in 1..number loop
		if i % 10000 = 0 then
			raise notice 'users_insert: %', i;
		end if;
		select random_string(16) into login; -- set login
		select random_string(64) into pass;  -- set password
		insert into users (login, password, enabled, time)
			values(login, pass, enabled, clock_timestamp());
	end loop;
end;
$$ language plpgsql;


select insert_users(10000000);
select count(id) from users;
