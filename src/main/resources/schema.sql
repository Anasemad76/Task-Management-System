-- Database: TaskManagementSystem
CREATE DATABASE TaskManagementSystem;
GO

-- Use the database
USE TaskManagementSystem;
GO

--users table
CREATE TABLE users (
    id INT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    is_admin bit NOT NULL
);
GO

-- tasks Table
CREATE TABLE Tasks (
    id INT IDENTITY(1,1) PRIMARY KEY,
    task_title VARCHAR(255) NOT NULL,
    task_description TEXT NULL,
    assigned_user VARCHAR(255) NULL,
    is_completed bit NOT NULL ,
    priority INT NULL,
    due_date DATE NULL,
);
GO