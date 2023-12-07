DROP DATABASE rentals;
CREATE DATABASE rentals;
USE rentals;

CREATE TABLE `USERS` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `email` varchar(255),
  `name` varchar(255),
  `password` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `RENTALS` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255),
  `surface` numeric,
  `price` numeric,
  `picture` varchar(255),
  `description` varchar(2000),
  `owner_id` integer NOT NULL,
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `MESSAGES` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `rental_id` integer,
  `user_id` integer,
  `message` varchar(2000),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE UNIQUE INDEX `USERS_index` ON `USERS` (`email`);

ALTER TABLE `RENTALS` ADD FOREIGN KEY (`owner_id`) REFERENCES `USERS` (`id`);

ALTER TABLE `MESSAGES` ADD FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`);

ALTER TABLE `MESSAGES` ADD FOREIGN KEY (`rental_id`) REFERENCES `RENTALS` (`id`);

INSERT INTO USERS (email, name, password, created_at) VALUES
  ('toto@gmail.com', 'toto', '$2a$10$lylN8VKwYVDpJeyL0aP.Re3TriFYQHoQ4kGlNKsoA14lPu2xQREnK', NOW()),
  ('titi@gmail.com', 'titi', '$2a$10$lylN8VKwYVDpJeyL0aP.Re3TriFYQHoQ4kGlNKsoA14lPu2xQREnK', NOW()),
  ('tata@gmail.com', 'tata', '$2a$10$lylN8VKwYVDpJeyL0aP.Re3TriFYQHoQ4kGlNKsoA14lPu2xQREnK', NOW());

INSERT INTO RENTALS (name, surface, price, picture, description, owner_id, created_at) VALUES
  ('location1', 20, 230, 'https://blog.technavio.org/wp-content/uploads/2018/12/Online-House-Rental-Sites.jpg', 'chambre chez l habitant', 1, NOW()),
  ('location2', 100, 600, 'https://blog.technavio.org/wp-content/uploads/2018/12/Online-House-Rental-Sites.jpg', 'maison', 2, NOW()),
  ('location3', 500, 1800, 'https://blog.technavio.org/wp-content/uploads/2018/12/Online-House-Rental-Sites.jpg', 'villa', 3, NOW()),
  ('location4', 50, 400, 'https://blog.technavio.org/wp-content/uploads/2018/12/Online-House-Rental-Sites.jpg', 'bungalow', 1, NOW());
  
INSERT INTO MESSAGES (rental_id, user_id, message, created_at) VALUES
  (1, 1, 'message du owner 1', NOW()),
  (2, 2, 'message du owner 2', NOW()),
  (3, 3, 'message du owner 3', NOW()),
  (4, 1, 'message du owner 1', NOW());