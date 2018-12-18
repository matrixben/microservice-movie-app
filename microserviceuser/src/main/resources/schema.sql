--drop table user if exists;
--postgresql
create table movie_user (
    user_id serial,
    username text,
    realname text,
    age int,
    user_email text,
    class_id int,
    user_password text,
    primary key (user_id)
);
create table user_class (
    class_id serial,
    class_name text,
    class_discount numeric(4,2),
    class_isactive boolean,
    primary key (class_id)
);
create table login_role (
    role_id serial,
    role_name text,
    description text,
    primary key (role_id)
);
--中间表
create table user_role (
    user_id int,
    role_id int
);