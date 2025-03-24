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

-- Hibernate

Hibernate:
create table HighPriorityTask (
                                  id int not null,
                                  primary key (id)
)
    Hibernate:
create table tasks (
                       id int identity not null,
                       due_date date,
                       is_completed BIT DEFAULT 0 not null,
                       priority INT CHECK (priority >= 1 AND priority <= 3) not null,
                       task_description varchar(255),
                       task_title varchar(255) not null,
                       user_id int,
                       primary key (id)
)
    Hibernate:
create table users (
                       type varchar(31) not null,
                       id int identity not null,
                       is_admin bit not null,
                       password varchar(255) not null,
                       username varchar(255) not null,
                       primary key (id)
)
    Hibernate:
alter table tasks
drop constraint if exists UK_lpovp2bhvoj832xvuw3ugijfk
Hibernate:
alter table tasks
    add constraint UK_lpovp2bhvoj832xvuw3ugijfk unique (task_title)
    Hibernate:
alter table users
drop constraint if exists UK_r43af9ap4edm43mmtq01oddj6
Hibernate:
alter table users
    add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username)
    Hibernate:
alter table HighPriorityTask
    add constraint FKrx8n1rqvk4pqahn874jeqdfay
        foreign key (id)
            references tasks
    Hibernate:
alter table tasks
    add constraint FK6s1ob9k4ihi75xbxe2w0ylsdh
        foreign key (user_id)
            references users