create table post(
    id serial primary key,
    name varchar(255) NOT NULL,
    text text NOT NULL,
    link text UNIQUE NOT NULL,
    created timestamp NOT NULL
);