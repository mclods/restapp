package com.mclods.restapp.services;

import com.mclods.restapp.domain.entities.BookEntity;

public interface BookService {
    BookEntity create(String isbn, BookEntity bookEntity);
}
