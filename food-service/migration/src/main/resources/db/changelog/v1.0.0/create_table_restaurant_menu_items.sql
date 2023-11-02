create sequence if not exists restaurant_menu_items_seq;

create table restaurant_menu_items
(
    id            bigserial
        constraint restaurant_menu_items_pk
            primary key,
    restaurant_id bigint      not null
        constraint "FK_restaurant_menu_items_restaurants"
            references restaurants,
    name          varchar(64) not null,
    price         numeric      not null,
    image         varchar(512) not null,
    description   varchar     not null
);

alter table restaurant_menu_items
    owner to postgres;

create unique index restaurant_menu_items_id_uindex
    on restaurant_menu_items (id);

comment on table restaurant_menu_items is 'Меню ресторана';
comment on column restaurant_menu_items.id is 'Идентификатор позиции';
comment on column restaurant_menu_items.restaurant_id is 'Идентификатор ресторана';
comment on column restaurant_menu_items.name is 'Название блюда';
comment on column restaurant_menu_items.price is 'Цена блюда';
comment on column restaurant_menu_items.image is 'Изображение блюда';
comment on column restaurant_menu_items.description is 'Описание блюда';