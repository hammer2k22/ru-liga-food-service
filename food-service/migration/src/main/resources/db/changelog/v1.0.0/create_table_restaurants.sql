create sequence if not exists customers_seq;

create table if not exists restaurants
(
    id      bigserial
        constraint restaurants_pk
            primary key,
    name    varchar(32) not null ,
    address varchar     not null,
    status  varchar(32) not null,
    coordinates varchar(32)     not null
);

alter table restaurants
    owner to postgres;

create unique index restaurants_id_uindex
    on restaurants (id);

comment on table restaurants is 'Список ресторанов';
comment on column restaurants.id is 'Идентификатор ресторана';
comment on column restaurants.name is 'Название ресторана';
comment on column restaurants.address is 'Адрес ресторана';
comment on column restaurants.status is 'Статус ресторана';
comment on column restaurants.coordinates is 'Координаты ресторана';