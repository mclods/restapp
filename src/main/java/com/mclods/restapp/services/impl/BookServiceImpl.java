package com.mclods.restapp.services.impl;

import com.mclods.restapp.domain.entities.BookEntity;
import com.mclods.restapp.repositories.BookRepository;
import com.mclods.restapp.services.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createBook(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }
}
