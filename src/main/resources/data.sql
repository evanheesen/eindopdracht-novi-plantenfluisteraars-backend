INSERT INTO employees (id, first_name, last_name, street, house_number, postal_code, city, email, phone)
VALUES
('1', 'John', 'de Bever', 'J.P. Heijestraat', '102', '1053MN', 'Amsterdam', 'john@plantenfluisteraars.nl', '0677881122');

INSERT INTO users (username, password, enabled, employee_id)
VALUES
('user', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE, null),
('admin', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE, null),
('eric', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE, 1);

INSERT INTO authorities (username, authority)
VALUES
('user', 'ROLE_USER'),
('admin', 'ROLE_USER'),
('admin', 'ROLE_ADMIN'),
('eric', 'ROLE_USER'),
('eric', 'ROLE_ADMIN');


