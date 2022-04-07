create table customer
(
    id         integer not null
        constraint customer_pkey
            primary key,
    email      varchar(255),
    first_name varchar(255),
    last_name  varchar(255)
);