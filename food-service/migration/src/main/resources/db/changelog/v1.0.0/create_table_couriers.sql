create sequence if not exists couriers_seq;

create table if not exists couriers
(
    id          bigserial
        constraint couriers_pk
            primary key,
    phone       varchar(16) not null,
    status      varchar(16) not null,
    coordinates varchar     not null
);

alter table couriers
    owner to postgres;

create unique index couriers_id_uindex
    on couriers (id);

create unique index couriers_phone_uindex
    on couriers (phone);

comment on table couriers is 'Список курьеров';
comment on column couriers.id is 'Идентификатор курьера';
comment on column couriers.phone is 'Телефон курьера';
comment on column couriers.status is 'Статус курьера';
comment on column couriers.coordinates is 'Координаты курьера';