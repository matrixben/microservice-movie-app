--影院票务管理表结构
--订单表
create table movie_order (
    order_id     serial,
    user_id      int,
    schedule_id  int,
    seat_number  int,
    total_price  numeric(10,2),
    buy_datetime timestamp with time zone,
    primary key (order_id)
);
--订单座位表,中间表
create table order_seat (
    order_id int,
    seat_id  int
);
--排片表
create table movie_schedule (
    schedule_id serial,
    movie_id               int,
    hall_id                int,
    schedule_price         numeric(10,2),
    schedule_begindatetime timestamp with time zone,
    primary key (schedule_id)
);
--电影表
create table movie (
    movie_id     serial,
    movie_name   text,
    director     text,
    main_actor   text,
    duration_min int,
    description  text,
    primary key (movie_id)
);
--影厅表
create table movie_hall (
    hall_id     serial,
    seat_number int,
    description text,
    primary key (hall_id)
);
--座位表
create table movie_seat (
    seat_id       serial,
    hall_id       int,
    seat_row      int,
    seat_col      int,
    seat_isactive boolean,
    primary key (seat_id)
);