drop table if exists public.books;
drop table if exists public.authors;
drop table if exists public.genres;

create table public.authors (
    author_id bigserial not null primary key,
    author_name varchar(255) not null
);

create table public.genres (
    genre_id bigserial not null primary key,
    genre_name varchar(255) not null
);

create table public.books (
    book_id bigserial not null primary key,
    author_id bigint not null references public.authors (author_id),
    genre_id bigint not null references public.genres (genre_id),
    title varchar(255) not null
);
