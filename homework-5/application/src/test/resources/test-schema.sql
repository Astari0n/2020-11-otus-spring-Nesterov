drop table if exists books;
drop table if exists authors;
drop table if exists genres;

create table authors (
    author_id bigserial not null primary key,
    author_name varchar(255) not null
);

create table genres (
    genre_id bigserial not null primary key,
    genre_name varchar(255) not null
);

create table books (
    book_id bigserial not null primary key,
    author_id bigint not null references authors (author_id),
    genre_id bigint not null references genres (genre_id),
    title varchar(255) not null
);
