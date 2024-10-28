CREATE DATABASE IF NOT EXISTS neoris_test CHARACTER SET utf8;

USE neoris_test;

-- Create table person
CREATE TABLE IF NOT EXISTS person (
    person_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    gender CHAR(1),
    age INT,
    identification VARCHAR(50) NOT NULL UNIQUE,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    create_by VARCHAR(50) NOT NULL,
    create_date DATETIME NOT NULL,
    modified_by VARCHAR(50),
    modified_date DATETIME
);

-- Create table client that inherits from person
CREATE TABLE IF NOT EXISTS client (
    client_id INT AUTO_INCREMENT PRIMARY KEY,
    person_id INT NOT NULL,
    password VARCHAR(255) NOT NULL,
    status CHAR(1) NOT NULL,
    UNIQUE (person_id),
    create_by VARCHAR(50) NOT NULL,
    create_date DATETIME NOT NULL,
    modified_by VARCHAR(50),
    modified_date DATETIME,
    FOREIGN KEY (person_id) REFERENCES person (person_id)
);

-- Create table account
CREATE TABLE IF NOT EXISTS account (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(20) NOT NULL,
    account_type VARCHAR(50) NOT NULL,
    initial_balance DECIMAL(15, 2) NOT NULL,
    status CHAR(1) NOT NULL,
    client_id INT NOT NULL,
    UNIQUE (account_number),
    create_by VARCHAR(50) NOT NULL,
    create_date DATETIME NOT NULL,
    modified_by VARCHAR(50),
    modified_date DATETIME,
    FOREIGN KEY (client_id) REFERENCES client (client_id)
);

-- Create table movements
CREATE TABLE IF NOT EXISTS movements (
    movement_id INT AUTO_INCREMENT PRIMARY KEY,
    date DATETIME NOT NULL,
    movement_type VARCHAR(50) NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    balance DECIMAL(15, 2) NOT NULL,
    status CHAR(1) NOT NULL,
    account_id INT NOT NULL,
    create_by VARCHAR(50) NOT NULL,
    create_date DATETIME NOT NULL,
    modified_by VARCHAR(50),
    modified_date DATETIME,
    FOREIGN KEY (account_id) REFERENCES account (account_id)
);

-- Insert data into person table
INSERT INTO person (name, gender, age, identification, address, phone, create_by, create_date)
VALUES 
('Jon Snow', 'M', 30, 'ID00001', 'The Wall', '555-0001', 'admin', '2024-10-01 10:00:00'),
('Daenerys Targaryen', 'F', 29, 'ID00002', 'Dragonstone', '555-0002', 'admin', '2024-10-01 10:00:00'),
('Tyrion Lannister', 'M', 39, 'ID00003', 'Casterly Rock', '555-0003', 'admin', '2024-10-01 10:00:00'),
('Arya Stark', 'F', 20, 'ID00004', 'Winterfell', '555-0004', 'admin', '2024-10-01 10:00:00'),
('Sansa Stark', 'F', 24, 'ID00005', 'Winterfell', '555-0005', 'admin', '2024-10-01 10:00:00');

-- Insert data into client table
INSERT INTO client (person_id, password, status, create_by, create_date)
VALUES 
(1, 'password123', '1', 'admin', '2024-10-01 10:00:00'),
(2, 'password456', '1', 'admin', '2024-10-01 10:00:00'),
(3, 'password789', '1', 'admin', '2024-10-01 10:00:00'),
(4, 'password111', '1', 'admin', '2024-10-01 10:00:00'),
(5, 'password222', '1', 'admin', '2024-10-01 10:00:00');

-- Insert data into account table
INSERT INTO account (account_number, account_type, initial_balance, status, client_id, create_by, create_date)
VALUES 
('ACC1001', 'Savings', 2500.00, '1', 1, 'admin', '2024-10-01 10:00:00'),
('ACC1002', 'Checking', 1500.00, '1', 2, 'admin', '2024-10-01 10:00:00'),
('ACC1003', 'Savings', 3000.00, '1', 3, 'admin', '2024-10-01 10:00:00'),
('ACC1004', 'Checking', 4000.00, '1', 4, 'admin', '2024-10-01 10:00:00'),
('ACC1005', 'Savings', 5000.00, '1', 5, 'admin','2024-10-01 10:00:00');

-- Insert data into movements table
INSERT INTO movements (date, movement_type, amount, balance, status, account_id, create_by, create_date)
VALUES 
-- Movimientos para la cuenta 1 (Balance inicial: 2500.00)
('2024-10-02 10:00:00', 'Deposit', 1000.00, 3500.00, '1', 1, 'admin', NOW()),  -- 2500 + 1000 = 3500
('2024-10-17 10:00:00', 'Withdrawal', 200.00, 3300.00, '1', 1, 'admin', NOW()),  -- 3500 - 200 = 3300
('2024-10-21 10:00:00', 'Deposit', 500.00, 3800.00, '1', 1, 'admin', NOW()),     -- 3300 + 500 = 3800
('2024-10-25 10:00:00', 'Deposit', 400.00, 4200.00, '1', 1, 'admin', NOW()),     -- 3800 + 400 = 4200

-- Movimientos para la cuenta 2 (Balance inicial: 1500.00)
('2024-10-05 10:00:00', 'Deposit', 700.00, 2200.00, '1', 2, 'admin', NOW()),     -- 1500 + 700 = 2200
('2024-10-10 10:00:00', 'Deposit', 1000.00, 3200.00, '1', 2, 'admin', NOW()),    -- 2200 + 1000 = 3200
('2024-10-26 10:00:00', 'Withdrawal', 300.00, 2900.00, '1', 2, 'admin', NOW()),  -- 3200 - 300 = 2900

-- Movimientos para la cuenta 3 (Balance inicial: 3000.00)
('2024-10-08 10:00:00', 'Withdrawal', 400.00, 2600.00, '1', 3, 'admin', NOW()),  -- 3000 - 400 = 2600
('2024-10-23 10:00:00', 'Deposit', 600.00, 3200.00, '1', 3, 'admin', NOW()),     -- 2600 + 600 = 3200
('2024-10-27 10:00:00', 'Withdrawal', 500.00, 2700.00, '1', 3, 'admin', NOW()), -- 3200 - 500 = 2700

-- Movimientos para la cuenta 4 (Balance inicial: 4000.00)
('2024-10-03 10:00:00', 'Deposit', 800.00, 4800.00, '1', 4, 'admin', NOW()),    -- 4000 + 800 = 4800
('2024-10-18 10:00:00', 'Withdrawal', 200.00, 4600.00, '1', 4, 'admin', NOW()), -- 4800 - 200 = 4600
('2024-10-24 10:00:00', 'Deposit', 1200.00, 5800.00, '1', 4, 'admin', NOW()),   -- 4600 + 1200 = 5800

-- Movimientos para la cuenta 5 (Balance inicial: 5000.00)
('2024-10-14 10:00:00', 'Withdrawal', 700.00, 4300.00, '1', 5, 'admin', NOW()), -- 5000 - 700 = 4300
('2024-10-15 10:00:00', 'Deposit', 1500.00, 5800.00, '1', 5, 'admin', NOW()),   -- 4300 + 1500 = 5800
('2024-10-27 10:00:00', 'Withdrawal', 600.00, 5200.00, '1', 5, 'admin', NOW()); -- 5800 - 600 = 5200


CREATE DATABASE IF NOT EXISTS neoris_integration_test;

CREATE TABLE IF NOT EXISTS neoris_integration_test.person LIKE neoris_test.person;
CREATE TABLE IF NOT EXISTS neoris_integration_test.client LIKE neoris_test.client;
CREATE TABLE IF NOT EXISTS neoris_integration_test.account LIKE neoris_test.account;
CREATE TABLE IF NOT EXISTS neoris_integration_test.movements LIKE neoris_test.movements;

INSERT INTO neoris_integration_test.person SELECT * FROM neoris_test.person;
INSERT INTO neoris_integration_test.client SELECT * FROM neoris_test.client;
INSERT INTO neoris_integration_test.account SELECT * FROM neoris_test.account;
INSERT INTO neoris_integration_test.movements SELECT * FROM neoris_test.movements;

GRANT ALL PRIVILEGES ON neoris_integration_test.* TO 'user'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;
