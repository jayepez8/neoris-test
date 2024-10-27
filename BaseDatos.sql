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
INSERT INTO person (name, gender, age, identification, address, phone, create_by, create_date, modified_by, modified_date)
VALUES 
('Alice Smith', 'F', 30, 'ID12345', '123 Main St', '555-1234', 'admin', NOW(), 'admin', NOW()),
('Bob Johnson', 'M', 42, 'ID67890', '456 Elm St', '555-5678', 'admin', DATE_SUB(NOW(), INTERVAL 3 DAY), 'admin', DATE_SUB(NOW(), INTERVAL 3 DAY));

-- Insert data into client table
INSERT INTO client (person_id, password, status, create_by, create_date, modified_by, modified_date)
VALUES 
(1, 'password123', '1', 'admin', NOW(), 'admin', NOW()),
(2, 'password456', '1', 'admin', DATE_SUB(NOW(), INTERVAL 3 DAY), 'admin', DATE_SUB(NOW(), INTERVAL 3 DAY));

-- Insert data into account table
INSERT INTO account (account_number, account_type, initial_balance, status, client_id, create_by, create_date, modified_by, modified_date)
VALUES 
('ACC1001', 'Savings', 1000.00, 'A', 1, 'admin', NOW(), 'admin', NOW()),
('ACC1002', 'Checking', 500.00, 'A', 2, 'admin', DATE_SUB(NOW(), INTERVAL 3 DAY), 'admin', DATE_SUB(NOW(), INTERVAL 3 DAY));

-- Insert data into movements table
INSERT INTO movements (date, movement_type, amount, balance, status, account_id, create_by, create_date, modified_by, modified_date)
VALUES 
(NOW(), 'Deposit', 200.00, 1200.00, 'A', 1, 'admin', NOW(), 'admin', NOW()),
(DATE_SUB(NOW(), INTERVAL 3 DAY), 'Withdrawal', 100.00, 900.00, 'A', 1, 'admin', DATE_SUB(NOW(), INTERVAL 3 DAY), 'admin', DATE_SUB(NOW(), INTERVAL 3 DAY)),
(NOW(), 'Deposit', 150.00, 650.00, 'A', 2, 'admin', NOW(), 'admin', NOW()),
(DATE_SUB(NOW(), INTERVAL 3 DAY), 'Withdrawal', 50.00, 450.00, 'A', 2, 'admin', DATE_SUB(NOW(), INTERVAL 3 DAY), 'admin', DATE_SUB(NOW(), INTERVAL 3 DAY));
