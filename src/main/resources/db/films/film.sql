CREATE TABLE film (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(128),
  genre VARCHAR(128),
  last TIME,
  price INT,
  image BLOB
);

