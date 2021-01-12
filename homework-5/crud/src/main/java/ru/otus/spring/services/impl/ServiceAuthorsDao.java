package ru.otus.spring.services.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import ru.otus.spring.dao.DaoAuthors;
import ru.otus.spring.model.Author;
import ru.otus.spring.services.AuthorsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceAuthorsDao implements AuthorsService {

    private final DaoAuthors daoAuthors;

    @Override
    public Author createAuthor(final String authorName) {
        return daoAuthors.create(authorName);
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
    public int deleteAuthor(final Author author) {
        return daoAuthors.delete(author);
    }
}
