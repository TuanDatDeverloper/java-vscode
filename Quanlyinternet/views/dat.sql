-- Tạo database ví dụ
CREATE DATABASE IF NOT EXISTS internetdb
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

USE internetdb;

-- Bảng lưu thông tin người dùng
CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  role VARCHAR(20) NOT NULL
);
CREATE TABLE customers (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  phone VARCHAR(20) NOT NULL,
  email VARCHAR(100) NULL,
  package VARCHAR(50) NOT NULL,
  register_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  total_amount DECIMAL(10,2) DEFAULT 0.00,
  active TINYINT(1) DEFAULT 1
);
CREATE TABLE transactions (
  id INT AUTO_INCREMENT PRIMARY KEY,
  customer_id INT NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  transaction_type ENUM('DEPOSIT', 'WITHDRAW') NOT NULL,
  transaction_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  description TEXT,
  FOREIGN KEY (customer_id) REFERENCES customers(id)
);
CREATE TABLE packages (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  duration INT NOT NULL, -- duration in days
  description TEXT,
  active TINYINT(1) DEFAULT 1
);
CREATE TABLE subscriptions (
  id INT AUTO_INCREMENT PRIMARY KEY,
  customer_id INT NOT NULL,
  package_id INT NOT NULL,
  start_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  end_date DATETIME,
  status ENUM('ACTIVE', 'EXPIRED', 'CANCELLED') DEFAULT 'ACTIVE',
  FOREIGN KEY (customer_id) REFERENCES customers(id),
  FOREIGN KEY (package_id) REFERENCES packages(id)
);

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- Add the new table creation statements here

SET FOREIGN_KEY_CHECKS = 1;