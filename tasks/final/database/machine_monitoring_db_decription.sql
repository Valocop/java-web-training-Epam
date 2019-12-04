-- PostgreSQL 11

-- Create db
create DATABASE machine_monitoring
    with
    OWNER = valocop
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

-- Create schema
create SCHEMA machine_monitoring_schema
    AUTHORIZATION valocop;

grant all on SCHEMA machine_monitoring_schema TO valocop;

alter DEFAULT PRIVILEGES IN SCHEMA machine_monitoring_schema
    grant all on TABLES to valocop;

-- Create table user_account
create table machine_monitoring_schema.user_account
(
    id BIGSERIAL not null,
    login VARCHAR(30) not null,
    password VARCHAR(50) not null,
    email VARCHAR(50) not null,
    name VARCHAR(40) not null,
    address VARCHAR(50) not null,
    tel VARCHAR(30) not null,
    picture bytea
);

create unique index user_account_id_uindex
    on machine_monitoring_schema.user_account (id);

create unique index user_account_login_uindex
    on machine_monitoring_schema.user_account (login);

alter table machine_monitoring_schema.user_account
    add constraint user_account_pk
        primary key (id);

-- Create table role
create table machine_monitoring_schema.role
(
    id SERIAL not null,
    name VARCHAR(30) not null,
    default_role BOOLEAN
);

create unique index role_id_uindex
    on machine_monitoring_schema.role (id);

create unique index role_name_uindex
    on machine_monitoring_schema.role (name);

alter table machine_monitoring_schema.role
    add constraint role_pk
        primary key (id);

insert into "machine_monitoring_schema"."role" ("id", "name", "default_role") values (1, 'DEFAULT', true)
insert into "machine_monitoring_schema"."role" ("id", "name", "default_role") values (2, 'ADMIN', null)
insert into "machine_monitoring_schema"."role" ("id", "name", "default_role") values (2, 'MANUFACTURER', null)

-- Create table machine_monitoring_schema.user_account_role_relation
create table machine_monitoring_schema.user_account_role_relation
(
    user_account_id BIGINT not null,
    role_id BIGINT not null
);

alter table machine_monitoring_schema.user_account_role_relation
    add constraint user_account_role_relation_role_id_fk
        foreign key (role_id) references machine_monitoring_schema.role;

alter table machine_monitoring_schema.user_account_role_relation
    add constraint user_account_role_relation_user_account_id_fk
        foreign key (user_account_id) references machine_monitoring_schema.user_account;

-- Create table machine_monitoring_schema.manufacture
create table machine_monitoring_schema.manufacture
(
    id BIGSERIAL not null,
    name VARCHAR(150) not null,
    user_id BIGINT not null
        constraint manufacture_user_account_id_fk
            references machine_monitoring_schema.user_account
);

create unique index manufacture_id_uindex
    on machine_monitoring_schema.manufacture (id);

alter table machine_monitoring_schema.manufacture
    add constraint manufacture_pk
        primary key (id);

-- Create table machine_monitoring_schema.machine_model
create table machine_monitoring_schema.machine_model
(
    id BIGSERIAL not null,
    name VARCHAR(100) not null,
    release_date date not null,
    picture bytea,
    description VARCHAR(200) not null,
    manufacture_id BIGINT not null
        constraint machine_model_manufacture_id_fk
            references machine_monitoring_schema.manufacture
);

create unique index machine_model_id_uindex
    on machine_monitoring_schema.machine_model (id);

alter table machine_monitoring_schema.machine_model
    add constraint machine_model_pk
        primary key (id);

-- Create table characteristic
create table characteristic
(
    id              bigserial    not null
        constraint characteristic_pk
            primary key,
    price           numeric      not null,
    power           varchar(150) not null,
    fuel_type       varchar(150) not null,
    engine_volume   varchar(100) not null,
    transmission    varchar(150) not null,
    manufacturer_id bigint       not null
        constraint characteristic_manufacture_id_fk
            references manufacture
);

alter table characteristic
    owner to valocop;

create unique index characteristic_id_uindex
    on characteristic (id);