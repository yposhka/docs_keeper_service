create table document_status
(
    name        varchar(100)     not null constraint document_status_pkey primary key,
    description varchar(300)
);

create table document_type
(
    name        varchar(100)     not null constraint document_type_pkey primary key,
    structure   json             not null,
    description varchar(300)
);

create table document
(
    id              bigint       not null generated always as identity constraint document_pkey primary key,
    user_id         bigint       not null constraint document_user_id references users,
    title           varchar(100) not null,
    data            text         not null,
    creation_date   timestamp    not null,
    expiration_date timestamp,
    type            varchar(100) not null constraint document_type_id references document_type,
    status          varchar(100) not null constraint document_status_id references document_status
);