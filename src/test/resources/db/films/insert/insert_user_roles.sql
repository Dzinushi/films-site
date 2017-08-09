-- GENERATE DATE FOR TABLE 'user_roles'

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


-- insert into 'user_roles'
Create or replace function insert_user_roles() returns void as $$
declare
	ur_login varchar(16) := '';
	ur_role varchar(45) := '';
	ur_time timestamp := '2017-07-31 00:00';
	role_rand integer := 0;
  number INT;
begin

	-- drop user_roles
	DROP TABLE IF EXISTS user_roles CASCADE;
	DROP SEQUENCE IF EXISTS user_roles_id_seq CASCADE;

	-- create user_roles
	CREATE SEQUENCE user_roles_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
	CREATE TABLE user_roles (
	  id    BIGINT NOT NULL DEFAULT nextval('user_roles_id_seq'::regclass),
	  login VARCHAR(16) NOT NULL REFERENCES users(login) ON DELETE SET NULL,
	  role  VARCHAR(45) NOT NULL,
	  time TIMESTAMP
	);

  select count(id) into number from users;

	-- insert user_roles
	for i in 1..number loop
		if i % 10000 = 0 then
			raise notice 'user_roles_insert: %', i;
		end if;
		-- set login
		select login into ur_login from users where id = i;

		-- set role
		role_rand = trunc(random() * 3000);
		if role_rand > 2990 then
			ur_role = 'ROLE_ADMIN';
		elsif role_rand = 0 then
			ur_role = 'ROLE_BANNED';
		else
			ur_role = 'ROLE_USER';
		end if;

		-- set time
		select time into ur_time from users where id = i;

		insert into user_roles (login, role, time)
			values(ur_login, ur_role, ur_time);
	end loop;
end;
$$ language plpgsql;

select insert_user_roles();
select * from user_roles limit 10;