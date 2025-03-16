package com.mclods.restapp.services;

import com.mclods.restapp.domain.entities.BookEntity;

public interface BookService {
    BookEntity createBook(String isbn, BookEntity bookEntity);
}
