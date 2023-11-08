create sequence if not exists orders_seq;

create table orders
(
    id     uuid    not null
        constraint orders_pk
            primary key,
    customer_id   bigint                  not null
        constraint "FK_orders_customers"
            references customers,
    restaurant_id bigint                  not null
        constraint "FK_orders_restaurant"
            references restaurants,
    status        varchar(32)             not null,
    courier_id    bigint
        constraint "FK-orders_couriers"
            references couriers,
    timestamp     timestamp not null default now()
);

alter table orders
    owner to postgres;

create unique index orders_id_uindex
    on orders (id);

comment on table orders is 'Список заказов';
comment on column orders.id is 'Идентификатор заказа';
comment on column orders.customer_id is 'Идентификатор покупателя';
comment on column orders. restaurant_id is 'Идентификатор ресторана';
comment on column orders.status is 'Статус заказа';
comment on column orders.courier_id is 'Идентификатор курьера';
comment on column orders.timestamp is 'Дата';