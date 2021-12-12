-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
/*
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

 */

drop table if exists client;
drop table if exists address;
drop table if exists phone;

-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
create
sequence hibernate_sequence start
with 1 increment by 1;

create table address
(
    id     bigint not null primary key,
    street varchar(300)
);

create table client
(
    id         bigint      not null primary key,
    name       varchar(50) not null,
    address_id bigint references address (id)
);

create table phone
(
    id        bigint      not null primary key,
    number    varchar(20) not null,
    client_id bigint references client (id)
);






