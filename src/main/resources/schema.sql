DROP TABLE IF EXISTS book;
create table if not exists book(
id serial primary key,
title varchar(255) not null
);