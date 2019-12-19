# DB description

![enter image description here](https://sun9-16.userapi.com/c200416/v200416342/15b5c/4TRvJCx2Mwo.jpg)


# SQL

    
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
    id bigserial   not null  
 constraint user_account_pk  
            primary key,  
  login varchar(30) not null,  
  password varchar(50) not null,  
  email varchar(50) not null,  
  name varchar(40) not null,  
  address varchar(50) not null,  
  tel varchar(30) not null,  
  picture bytea  
);  
  
alter table machine_monitoring_schema.user_account  
    owner to valocop;  
  
create unique index user_account_id_uindex  
    on machine_monitoring_schema.user_account (id);  
  
create unique index user_account_login_uindex  
    on machine_monitoring_schema.user_account (login);  
  
-- Create table role  
create table machine_monitoring_schema.role  
(  
    id serial      not null  
 constraint role_pk  
            primary key,  
  name varchar(30) not null,  
  default_role boolean  
);  
  
alter table machine_monitoring_schema.role  
    owner to valocop;  
  
create unique index role_id_uindex  
    on machine_monitoring_schema.role (id);  
  
create unique index role_name_uindex  
    on machine_monitoring_schema.role (name);  
  
insert into "machine_monitoring_schema"."role" ("id", "name", "default_role")  
values (1, 'DEFAULT', true);  
insert into "machine_monitoring_schema"."role" ("id", "name", "default_role")  
values (2, 'ADMIN', null);  
insert into "machine_monitoring_schema"."role" ("id", "name", "default_role")  
values (2, 'MANUFACTURER', null);  
  
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
    id bigserial    not null  
 constraint manufacture_pk  
            primary key,  
  name varchar(150) not null,  
  user_id bigint       not null  
 constraint manufacture_user_account_id_fk  
            references machine_monitoring_schema.user_account  
);  
  
alter table machine_monitoring_schema.manufacture  
    owner to valocop;  
  
create unique index manufacture_id_uindex  
    on machine_monitoring_schema.manufacture (id);  
  
-- Create table machine_monitoring_schema.machine_model  
create table machine_monitoring_schema.machine_model  
(  
    id bigserial    not null  
 constraint machine_model_pk  
            primary key,  
  name varchar(100) not null,  
  release_date date not null,  
  picture bytea,  
  description varchar(200) not null,  
  manufacture_id bigint       not null  
 constraint machine_model_manufacture_id_fk  
            references machine_monitoring_schema.manufacture  
);  
  
alter table machine_monitoring_schema.machine_model  
    owner to valocop;  
  
create unique index machine_model_id_uindex  
    on machine_monitoring_schema.machine_model (id);  
  
-- Create table characteristic  
create table machine_monitoring_schema.characteristic  
(  
    id bigserial    not null  
 constraint characteristic_pk  
            primary key,  
  price numeric      not null,  
  power varchar(150) not null,  
  fuel_type varchar(150) not null,  
  engine_volume varchar(100) not null,  
  transmission varchar(150) not null,  
  manufacturer_id bigint       not null  
 constraint characteristic_manufacture_id_fk  
            references machine_monitoring_schema.manufacture  
);  
  
alter table machine_monitoring_schema.characteristic  
    owner to valocop;  
  
create unique index characteristic_id_uindex  
    on machine_monitoring_schema.characteristic (id);  
  
-- Create table machine  
create table machine_monitoring_schema.machine  
(  
    id bigserial    not null  
 constraint machine_pk  
            primary key,  
  uniq_code varchar(100) not null,  
  model_id bigint       not null  
 constraint machine_machine_model_id_fk  
            references machine_monitoring_schema.machine_model,  
  characteristic_id bigint       not null  
 constraint machine_characteristic_id_fk  
            references machine_monitoring_schema.characteristic,  
  manufacture_id bigint       not null  
 constraint machine_manufacture_id_fk  
            references machine_monitoring_schema.manufacture  
);  
  
alter table machine_monitoring_schema.machine  
    owner to valocop;  
  
create unique index machine_id_uindex  
    on machine_monitoring_schema.machine (id);  
  
create unique index machine_uniq_code_uindex  
    on machine_monitoring_schema.machine (uniq_code);  
  
-- Create table machine_log  
create table machine_monitoring_schema.machine_log  
(  
    id bigserial not null  
 constraint machine_log_pk  
            primary key,  
  date date not null,  
  fuel_level numeric   not null,  
  oil_pressure numeric   not null,  
  oil_level numeric   not null,  
  coolant_temp numeric   not null,  
  machine_id bigint    not null  
 constraint machine_log_machine_id_fk  
            references machine_monitoring_schema.machine  
);  
  
alter table machine_monitoring_schema.machine_log  
    owner to valocop;  
  
create unique index machine_log_id_uindex  
    on machine_monitoring_schema.machine_log (id);  
  
-- Create table machine_error  
create table machine_monitoring_schema.machine_error  
(  
    id bigserial    not null  
 constraint machine_error_pk  
            primary key,  
  date date not null,  
  error_code varchar(200) not null,  
  machine_id bigint       not null  
 constraint machine_error_machine_id_fk  
            references machine_monitoring_schema.machine  
);  
  
alter table machine_monitoring_schema.machine_error  
    owner to valocop;  
  
create unique index machine_error_id_uindex  
    on machine_monitoring_schema.machine_error (id);  
  
-- Create table user_machine  
create table machine_monitoring_schema.user_machine  
(  
    user_id bigint not null  
 constraint user_machine_user_account_id_fk  
            references machine_monitoring_schema.user_account,  
  machine_id bigint not null  
 constraint user_machine_machine_id_fk  
            references machine_monitoring_schema.machine  
);