INSERT INTO app_user (user_id,name, email, password, created, modified, last_login, token, is_active)
VALUES (
   1,
  'admin',
  'admin@bci.cl',
  '$2y$10$y.TeZdP.tu0.i73euDQ2KOuKP4Imui0BvzF4ZFAewLIMwS.3CzT86',
  CURRENT_TIMESTAMP,
  CURRENT_TIMESTAMP,
  CURRENT_TIMESTAMP,
  'token123',
  TRUE
 );

INSERT INTO app_phone (phone_id, number, city_code, country_code, user_id)
VALUES (1,'1234567890', '123', '1',1);