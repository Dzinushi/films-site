CREATE TABLE user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  login VARCHAR(16) UNIQUE,
  password VARCHAR(64),
  created_at timestamp,
  is_deleted boolean,
  deleted_at timestamp
);