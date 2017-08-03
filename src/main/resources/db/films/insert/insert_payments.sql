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