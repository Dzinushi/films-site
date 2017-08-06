-- GENERATE DATE FOR TABLE 'films'

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


-- insert into 'films'
Create or replace function insert_film(number integer) returns void as $$
declare
	f_name VARCHAR(20);
	f_genre VARCHAR(1);
	f_duration SMALLINT;
	f_price INT;
	f_image VARCHAR(20);
	image_rand INT;
begin
	-- drop films
	DROP TABLE IF EXISTS films CASCADE;
	DROP SEQUENCE IF EXISTS films_id_seq CASCADE;

	-- create films
	CREATE SEQUENCE films_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
	CREATE TABLE films (
	  id BIGINT PRIMARY KEY DEFAULT nextval('films_id_seq'::regclass),
	  name VARCHAR(128),
	  genre VARCHAR(128),
	  duration SMALLINT,
	  price INT,
	  image VARCHAR(256) UNIQUE,
	  time TIMESTAMP
	);

	-- insert films
	for i in 1..number loop

		if i % 10000 = 0 then
			raise notice 'films_insert: %', i;
		end if;

		select random_string(20) into f_name;    -- set name
		select random_string(1) into f_genre;   -- set genre
		f_duration := trunc(random() * 280) + 20; -- set duration
		f_price := trunc(random() * 10000);       -- set price

		-- set image
		select random_string(15) into f_image;
		image_rand := trunc(random() * 4);
		if image_rand = 0 then
			f_image = f_image || '.jpg';
		elsif image_rand = 1 then
			f_image = f_image || '.jpeg';
		elsif image_rand = 2 then
			f_image = f_image || '.png';
		else
			f_image = f_image || '.bmp';
		end if;

		-- insert data
		insert into films (name, genre, duration, price, image, time)
			values(f_name, f_genre, f_duration, f_price, f_image, clock_timestamp());
	end loop;
end;
$$ language plpgsql;


select insert_film(1000000);
select count(id) from films;
