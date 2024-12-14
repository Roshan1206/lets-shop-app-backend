create database `lets-shop-app`;

use `lets-shop-app`;

create table cart (
        product_price float(53),
        product_quantity integer,
        total_product_price float(53),
        id bigint not null auto_increment,
        product_id bigint,
        email varchar(255),
        product_name varchar(255),
        product_thumbnail varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table country (
        id bigint not null auto_increment,
        code varchar(255),
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table orders (
        product_quantity integer,
        total_price float(53),
        date_created datetime(6),
        id bigint not null auto_increment,
        product_id bigint,
        email varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table product (
        price float(53),
        category bigint,
        id bigint not null auto_increment,
        stock bigint,
        brand varchar(255),
        description varchar(255),
        name varchar(255),
        thumbnail varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table product_category (
        id bigint not null auto_increment,
        product_category varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table state (
        country_id integer,
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;
    
    create table user (
        email varchar(255),
        firstname varchar(255),
        id varchar(255) not null,
        lastname varchar(255),
        password varchar(255),
        role enum ('ADMIN','EMPLOYEE','USER'),
        primary key (id)
    ) engine=InnoDB