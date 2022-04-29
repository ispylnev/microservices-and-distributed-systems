create table notification
(
    notification_id   integer not null
        constraint notification_pkey
            primary key,
    message           varchar(255),
    sender            varchar(255),
    sent_at           timestamp,
    to_customer_email varchar(255),
    to_customer_id    integer
);

alter table notification
    owner to customer;

