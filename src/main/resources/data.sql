INSERT INTO app_user (user_id,name, email, password, created, modified, last_login, token, is_active)
VALUES (
   1,
  'admin',
  'admin@bci.cl',
  '$2y$10$y.TeZdP.tu0.i73euDQ2KOuKP4Imui0BvzF4ZFAewLIMwS.3CzT86',
  '2015-08-12T10:27:25',
  NULL,
  CURRENT_TIMESTAMP,
  NULL,
  TRUE),
   (2,
   'Lionel Messi',
   'c',
   '$2y$10$pJiSnDOXrzlETsh82qseY.d7axq/CQLVYJsDUTW7dHvNGueGtGn56',
   '2021-08-12T10:27:25',
   NULL,
   CURRENT_TIMESTAMP,
   NULL,
   TRUE
  );

INSERT INTO app_phone (phone_id, number, city_code, country_code, user_id)
VALUES (1,'2222222', '34', '5',1),
 (2,'3333333', '123', '1',1),
 (3,'4444444', '432', '1',2);