package com.mclods.restapp.services.impl;

import com.mclods.restapp.domain.entities.BookEntity;
import com.mclods.restapp.repositories.BookRepository;
import com.mclods.restapp.services.BookService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity create(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }

    @Override
    public List<BookEntity> findAll() {
        List<BookEntity> books = new ArrayList<>();
        bookRepository.findAll().forEach(books::add);
        return books;
    }
}
