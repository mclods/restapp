package com.mclods.restapp.services;

import com.mclods.restapp.domain.entities.BookEntity;

import java.util.List;

public interface BookService {
    BookEntity create(String isbn, BookEntity bookEntity);
    List<BookEntity> findAll();
}
