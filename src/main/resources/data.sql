INSERT INTO customers (customer_id, first_name, last_name, phone, garden_id)
VALUES
(1000, 'Karin', 'Bloemen', '0677881122', (SELECT garden_id from gardens WHERE garden_id=3000)),
(1001, 'Pascal', 'Struijk', '0644556677', (SELECT garden_id from gardens WHERE garden_id=3001));

INSERT INTO employees (employee_id, first_name, last_name, street, house_number, postal_code, city, phone, status)
VALUES
(2000, 'Jeroen', 'van de Boom', 'J.P. Heijestraat', '102', '1053MN', 'Amsterdam', '+31612345678', 'Inactief'),
(2001, 'Stef', 'Bos', 'Keizersgracht', '232', '1014LV', 'Amsterdam', '+31633228899', 'Actief');

INSERT INTO gardens (garden_id, submission_date, street, house_number, postal_code, city, package_plants, status, customer_id, employee_id)
VALUES
(3000, '2021-11-14', 'Westerstraat', '57', '1015TP', 'Amsterdam', 'Pakket Wintergroen', 'Open', (SELECT customer_id from customers WHERE customer_id=1000), NULL),
(3001, '2021-11-13', 'Kinkerstraat', '71', '1054ZL', 'Amsterdam', 'Pakket Kleurrijk Laag', 'Actief', (SELECT customer_id from customers WHERE customer_id=1001), (SELECT employee_id from employees WHERE employee_id=2));

INSERT INTO users (username, password, enabled, email, customer_id, employee_id)
VALUES
('user', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE, 'user@hotmail.nl', NULL, NULL),
('eric', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE, 'eric@hotmail.nl', NULL, NULL),
('admin', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE, 'admin@hotmail.nl', NULL, NULL),
('karin', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE, 'karin@hotmail.nl', (SELECT customer_id from customers WHERE customer_id=1000), NULL),
('pascal', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE, 'pascal@hotmail.nl', (SELECT customer_id from customers WHERE customer_id=1001), NULL),
('stef', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE, 'stef@plantenfluisteraars.nl', NULL, (SELECT employee_id from employees WHERE employee_id=2001)),
('jeroen', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE, 'jeroen@plantenfluisteraars.nl', NULL, (SELECT employee_id from employees WHERE employee_id=2000));

INSERT INTO authorities (username, authority)
VALUES
('user', 'ROLE_USER'),
('admin', 'ROLE_USER'),
('admin', 'ROLE_ADMIN'),
('eric', 'ROLE_USER'),
('eric', 'ROLE_ADMIN'),
('karin', 'ROLE_USER'),
('pascal', 'ROLE_USER'),
('stef', 'ROLE_USER'),
('jeroen', 'ROLE_USER');


