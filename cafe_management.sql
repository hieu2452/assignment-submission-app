create database cafe_management;

CREATE USER 'springuser'@'localhost' IDENTIFIED BY 'password';

GRANT ALL PRIVILEGES ON cafe_management.* TO 'springuser'@'localhost';

use cafe_management;
insert into roles(name) values('manager');
insert into roles(name) values('employee');
