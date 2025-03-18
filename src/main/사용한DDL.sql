drop database if exists korweb;
create database korweb;
use korweb;

create table products(
	id int auto_increment ,
    name varchar(100),
    price int ,
    constraint primary key(id)
);