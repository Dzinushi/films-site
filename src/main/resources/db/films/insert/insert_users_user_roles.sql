INSERT INTO
  users(login,password,enabled)
VALUES
  ('dzinushi','5126', 1);
INSERT INTO
  users(login,password,enabled)
VALUES
  ('fabyloz','8743', 1);

INSERT INTO
  user_roles (login, role)
VALUES
  ('dzinushi', 'ROLE_USER');
INSERT INTO
  user_roles (login, role)
VALUES
  ('dzinushi', 'ROLE_ADMIN');
INSERT INTO
  user_roles (login, role)
VALUES
  ('fabyloz', 'ROLE_USER');