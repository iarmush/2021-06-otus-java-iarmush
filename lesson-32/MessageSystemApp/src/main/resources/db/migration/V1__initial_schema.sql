create table client
(
    id bigserial not null primary key,
    login varchar(50),
    role  varchar(50)
);

insert into client(login, role)
values ('default_user', 'USER');
insert into client(login, role)
values ('default_admin', 'ADMIN');
