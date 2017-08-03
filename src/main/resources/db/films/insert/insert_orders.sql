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