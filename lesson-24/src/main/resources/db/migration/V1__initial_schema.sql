-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
/*
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

 */

-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
create sequence hibernate_sequence start with 3 increment by 1;

create table client
(
    id   bigint not null primary key,
    login varchar(50),
    role varchar(50),
    password varchar(100)
);

insert into client (id, login, role, password) VALUES (1, 'admin', 'ADMIN', 'admin');
insert into client (id, login, role, password) VALUES (2, 'user', 'USER', 'user');