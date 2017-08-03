-------------------------------------------
-- users ----------------------------------
-------------------------------------------
INSERT INTO  users(login, password, enabled)
VALUES  ('dzinushi', '5126', 1);

INSERT INTO  users(login, password, enabled)
VALUES ('fabyloz', '8743', 1);

INSERT INTO users(login, password, enabled)
VALUES ('psi_cat', 'qwerty', 1);

INSERT INTO users(login, password, enabled)
VALUES ('ChEbUrEcHeCk', '1001', 0);

INSERT INTO users(login, password, enabled)
VALUES ('Burbonka', '287391', 0);
-------------------------------------------
-- user_roles -----------------------------
-------------------------------------------
INSERT INTO  user_roles (login, role)
VALUES  ('dzinushi', 'ROLE_ADMIN');

INSERT INTO  user_roles (login, role)
VALUES  ('fabyloz', 'ROLE_USER');

INSERT INTO user_roles (login, role)
VALUES ('psi_cat', 'ROLE_ADMIN');

INSERT INTO user_roles (login, role)
VALUES ('ChEbUrEcHeCk', 'ROLE_BANNED');

INSERT INTO user_roles (login, role)
VALUES ('Burbonka', 'ROLE_USER');
-------------------------------------------
-- discounts ------------------------------
-------------------------------------------
INSERT INTO discounts (code, value)
VALUES ('CNjXZApX4zWy4j09', 0.15);

INSERT INTO discounts (code, value)
VALUES ('NPPMoLQqroX2NfgE', 0.1);

INSERT INTO discounts (code, value)
VALUES ('sbVQzqRLPVscD48P', 0.15);

INSERT INTO discounts (code, value)
VALUES ('JI1Etj1DR2gQ5mNH', 0.2);

INSERT INTO discounts (code, value)
VALUES ('p2TdlP1aswG8I2kG', 0.25);
-------------------------------------------
-- user_discounts -------------------------
-------------------------------------------
INSERT INTO user_discounts (user_id, discount_id, used)
VALUES (1, 1, FALSE );

INSERT INTO user_discounts (user_id, discount_id, used)
VALUES (1, 2, FALSE );

INSERT INTO user_discounts (user_id, discount_id, used)
VALUES (5, 3, FALSE );

INSERT INTO user_discounts (user_id, discount_id, used)
VALUES (5, 4, FALSE );

INSERT INTO user_discounts (user_id, discount_id, used)
VALUES (5, 5, FALSE );
--------------------------------------------------------
-- films -----------------------------------------------
--------------------------------------------------------
-- duration in minutes ---------------------------------
-- price in dollars (cents) (All peoples / all money) --
--------------------------------------------------------
INSERT INTO films (name, genre, duration, price, image)
VALUES ('Крестный отец', 'драма,криминал', 175, 212,
        '/media/linux_ssd/Soft/Development/sites/films/films_images/Крёстный отец.jpg');

INSERT INTO films (name, genre, duration, price, image)
VALUES ('Побег из Шоушенка', 'драма,криминал', 142, 700,
        '/media/linux_ssd/Soft/Development/sites/films/films_images/Побег из Шоушенка.jpg');

INSERT INTO films (name, genre, duration, price, image)
VALUES ('Список Шиндлера', 'драма,биография,история', 195, 558,
        '/media/linux_ssd/Soft/Development/sites/films/films_images/Список Шиндлера.jpg');

INSERT INTO films (name, genre, duration, price, image)
VALUES ('Бешеный бык', 'драма,биография,спорт', 129, 270,
        '/media/linux_ssd/Soft/Development/sites/films/films_images/Бешеный бык.jpg');

INSERT INTO films (name, genre, duration, price, image)
VALUES ('Касабланка', 'драма,мелодрама,военный', 103, 307,
        '/media/linux_ssd/Soft/Development/sites/films/films_images/Крестный отец 2.jpg');
-------------------------------------------
-- basket ---------------------------------
-------------------------------------------
INSERT INTO baskets (user_id, time)
VALUES (1, clock_timestamp());

INSERT INTO baskets (user_id, time)
VALUES (2, clock_timestamp());

INSERT INTO baskets (user_id, time)
VALUES (5, clock_timestamp());
-------------------------------------------
-- orders ---------------------------------
-------------------------------------------
INSERT INTO orders (basket_id, film_id, discount_id, price_by_discount, mark, time)
VALUES (1, 1, NULL, NULL, TRUE, clock_timestamp());

INSERT INTO orders (basket_id, film_id, discount_id, price_by_discount, mark, time)
VALUES (1, 2, NULL, NULL, TRUE, clock_timestamp());

INSERT INTO orders (basket_id, film_id, discount_id, price_by_discount, mark, time)
VALUES (2, 3, NULL, NULL, TRUE, clock_timestamp());

INSERT INTO orders (basket_id, film_id, discount_id, price_by_discount, mark, time)
VALUES (3, 4, NULL, NULL, TRUE, clock_timestamp());

INSERT INTO orders (basket_id, film_id, discount_id, price_by_discount, mark, time)
VALUES (3, 5, NULL, NULL, TRUE, clock_timestamp());

INSERT INTO orders (basket_id, film_id, discount_id, price_by_discount, mark, time)
VALUES (3, 1, NULL, NULL, TRUE, clock_timestamp());
-------------------------------------------
-- payments -------------------------------
-------------------------------------------
INSERT INTO payments (user_id, film_id, discount_id, total_price, time)
VALUES (1, 1, NULL, 212, clock_timestamp());

INSERT INTO payments (user_id, film_id, discount_id, total_price, time)
VALUES (1, 2, NULL, 700, clock_timestamp());

INSERT INTO payments (user_id, film_id, discount_id, total_price, time)
VALUES (2, 3, NULL, 558, clock_timestamp());

INSERT INTO payments (user_id, film_id, discount_id, total_price, time)
VALUES (5, 4, NULL, 270, clock_timestamp());

INSERT INTO payments (user_id, film_id, discount_id, total_price, time)
VALUES (5, 5, NULL, 307, clock_timestamp());

INSERT INTO payments (user_id, film_id, discount_id, total_price, time)
VALUES (5, 1, NULL, 212, clock_timestamp());