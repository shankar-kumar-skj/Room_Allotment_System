-- database.sql
CREATE DATABASE IF NOT EXISTS college_allotment;
USE college_allotment;
-- drop database college_allotment;
-- users: id, username, password(hash), role, priority
CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(256) NOT NULL,
  role VARCHAR(50) NOT NULL,
  priority INT NOT NULL
);

-- students table
CREATE TABLE IF NOT EXISTS students (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(150) NOT NULL,
  roll_no VARCHAR(50) UNIQUE NOT NULL,
  department VARCHAR(100),
  email VARCHAR(150)
);

-- rooms table
CREATE TABLE IF NOT EXISTS rooms (
  id INT AUTO_INCREMENT PRIMARY KEY,
  room_no VARCHAR(50) NOT NULL UNIQUE,
  capacity INT NOT NULL,
  details VARCHAR(255)
);

-- allocations table
CREATE TABLE IF NOT EXISTS allocations (
  id INT AUTO_INCREMENT PRIMARY KEY,
  student_id INT NOT NULL,
  room_id INT NOT NULL,
  allocated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
  FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE,
  UNIQUE KEY unique_student (student_id)
);

-- Seed users (passwords are SHA-256 of the plaintext shown in comments)
-- admin / admin123  -> hash = 240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9
-- hod   / hod123    -> hash = 5c8473579466adb756fa9e042efc8d7756217c5f4c950731fcf96bd65ba184e9
-- student / student123 -> hash = 703b0a3d6ad75b649a28adde7d83c6251da457549263bc7ff45ec709b0a8448b

INSERT IGNORE INTO users (username, password, role, priority) VALUES
('admin', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'Admin', 100),
('hod',   '5c8473579466adb756fa9e042efc8d7756217c5f4c950731fcf96bd65ba184e9', 'HOD', 90),
('student', '703b0a3d6ad75b649a28adde7d83c6251da457549263bc7ff45ec709b0a8448b', 'Student', 10);

-- Example student + room rows
INSERT IGNORE INTO students (name, roll_no, department, email) VALUES
('Alice Kumar', 'PCE22CS001', 'Computer Science', 'alice@poornima.edu'),
('Bob Singh',   'PCE22IT002', 'Information Technology', 'bob@poornima.edu');

INSERT IGNORE INTO rooms (room_no, capacity, details) VALUES
('A-101', 2, 'First floor, near admin'),
('B-201', 3, 'Second floor, east wing');
