CREATE TABLE basket(
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT,
  film_id BIGINT,
  discount_id BIGINT
);

  ALTER TABLE basket ADD FOREIGN KEY(user_id) REFERENCES user(id);
  ALTER TABLE basket ADD FOREIGN KEY(film_id) REFERENCES film(id);
  ALTER TABLE basket ADD FOREIGN KEY(discount_id) REFERENCES discount(id);