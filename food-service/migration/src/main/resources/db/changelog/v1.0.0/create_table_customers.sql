create sequence if not exists customers_seq;

create table if not exists customers
(
    id      bigserial
        constraint customers_pk
            primary key,
    phone   varchar(16) not null,
    email   varchar(64) not null,
    address varchar     not null,
    coordinates varchar(32)     not null
);

alter table customers
    owner to postgres;

create unique index customers_id_uindex
    on customers (id);

create unique index customers_phone_uindex
    on customers (phone);

create unique index customers_email_uindex
    on customers (email);

comment on table customers is 'Список клиентов';
comment on column customers.id is 'Идентификатор клиента';
comment on column customers.phone is 'Телефон клиента';
comment on column customers.email is 'Адрес электронной почты клиента';
comment on column customers.address is 'Адрес клиента';
comment on column customers.coordinates is 'Координаты клиента';