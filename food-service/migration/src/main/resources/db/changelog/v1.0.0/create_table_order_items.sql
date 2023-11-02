create sequence if not exists order_items_seq;

create table order_items
(
    id                      bigserial
        constraint order_items_pk
            primary key,
    order_id                bigint  not null
        constraint "FK_order_items_orders"
            references orders,
    restaurant_menu_item_id bigint  not null
        constraint "FK_order_items_restaurant_menu_items"
            references restaurant_menu_items,
    price                   numeric  not null,
    quantity                integer not null
);

alter table order_items
    owner to postgres;

create unique index order_items_id_uindex
    on order_items (id);

comment on table order_items is 'Список блюд в заказе';
comment on column order_items.id is 'Идентификатор блюда в заказе';
comment on column order_items.order_id is 'Идентификатор заказа';
comment on column order_items.restaurant_menu_item_id is 'Идентификатор блюда в меню';
comment on column order_items.price is 'Цена';
comment on column order_items.quantity is 'Количество блюд';