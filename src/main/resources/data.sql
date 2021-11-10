--INSERT INTO employees (id, first_name, last_name, street, house_number, postal_code, city, email, phone)
--VALUES
--('1', 'John', 'de Bever', 'J.P. Heijestraat', '102', '1053MN', 'Amsterdam', 'john@hotmail.nl', '0677881122');

--INSERT INTO gardens (id, date, street, house_number, postal_code, city, package_plants)
--VALUES
--('1', '09-11-2021', 'J.P. Heijestraat', '102', '1053MN', 'Amsterdam', '2');

INSERT INTO users (username, password, enabled, email)
VALUES
('user', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE, 'user@hotmail.nl'),
('admin', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE, 'admin@hotmail.nl'),
('eric', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE, 'eric@hotmail.nl');

INSERT INTO authorities (username, authority)
VALUES
('user', 'ROLE_USER'),
('admin', 'ROLE_USER'),
('admin', 'ROLE_ADMIN'),
('eric', 'ROLE_USER'),
('eric', 'ROLE_ADMIN');


