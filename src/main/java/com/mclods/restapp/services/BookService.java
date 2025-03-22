package com.mclods.restapp.services;

import com.mclods.restapp.domain.entities.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookService {
    BookEntity save(String isbn, BookEntity bookEntity);
    List<BookEntity> findAll();
    Optional<BookEntity> findOne(String isbn);
    boolean exists(String isbn);
    BookEntity partialUpdate(String isbn, BookEntity bookEntity);
}
