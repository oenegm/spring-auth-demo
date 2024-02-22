create extension if not exists "uuid-ossp";

create table if not exists users
(
    id        uuid primary key      default uuid_generate_v4(),
    username  varchar(255) not null,
    system_id uuid,
    deleted   boolean      not null default false
);

create index if not exists users_username_key on users (username);