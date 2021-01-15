package ru.otus.spring.services.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import ru.otus.spring.dao.DaoAuthors;
import ru.otus.spring.exception.DeletionException;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.services.ServiceAuthors;
import ru.otus.spring.services.ServiceBooks;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceAuthorsDao implements ServiceAuthors {

    private final DaoAuthors daoAuthors;

    private final ServiceBooks serviceBooks;

    @Override
    public Author createAuthor(final String authorName) {
        final var author = new Author(authorName);

        long authorId = daoAuthors.insert(author);
        author.setId(authorId);

        return author;
    }

    @Override
    public int renameAuthor(final Author author, final String newAuthorName) {
        author.setName(newAuthorName);
        return daoAuthors.update(author);
    }

    @Override
    public Author findAuthorByAuthorId(final long authorId) {
        return daoAuthors.getById(authorId);
    }

    @Override
    public List<Author> getAll() {
        return daoAuthors.getAll();
    }

    @Override
    public int deleteAuthor(final Author author) throws DeletionException {
        final var books = serviceBooks.findBookByAuthorId(author);

        if (!books.isEmpty()) {
            throw new DeletionException(String.format(
                "there are books [%s] with authorId %d",
                books.stream().map(b -> String.valueOf(b.getId())).collect(Collectors.joining(",")),
                author.getId())
            );
        }

        return daoAuthors.delete(author);
    }
}
