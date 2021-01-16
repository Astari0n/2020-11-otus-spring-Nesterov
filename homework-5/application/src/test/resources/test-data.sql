insert into public.authors (author_name) values ('Ray Bradbury');
insert into public.authors (author_name) values ('Joanne Rowling');
insert into public.authors (author_name) values ('Free Author without books');

insert into public.genres (genre_name) values ('Novel');
insert into public.genres (genre_name) values ('Fantasy Novel');
insert into public.genres (genre_name) values ('Free Genre without books');

insert into public.books (author_id, genre_id, title) values (1, 1, 'Dandelion Wine');
insert into public.books (author_id, genre_id, title) values (2, 2, 'Harry Potter');
