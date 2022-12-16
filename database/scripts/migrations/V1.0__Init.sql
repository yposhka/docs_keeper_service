create table user_status
(
    name        varchar(45)    not null constraint user_status_pkey primary key,
    description varchar(100)
);

create table users
(
    id            bigint       not null constraint users_pkey primary key,
    login         varchar(320) not null,
    hash_password varchar(100) not null,
    salt          varchar(16)  not null,
    status        varchar(45)  not null constraint user_status_id references user_status
);

create table token
(
    token           varchar(32)  not null constraint token_pkey primary key,
    user_id         bigint       not null constraint token references users,
    device_id       varchar(300) not null,
    expiration_date timestamp    not null
);

insert into status (status, description) values
('unconfirmed', 'Регистрация аккаунта не завершена, нужен код подтверждения отправленный на почту'),
('confirmed', 'Регистрация аккаунта завершена');