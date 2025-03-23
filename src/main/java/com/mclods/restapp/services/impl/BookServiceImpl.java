package com.mclods.restapp.services.impl;

import com.mclods.restapp.domain.entities.BookEntity;
import com.mclods.restapp.repositories.BookRepository;
import com.mclods.restapp.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    public static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity save(String isbn, BookEntity bookEntity) {
        logger.info("save book service called");
        logger.debug("save with isbn: {}, bookEntity: {}", isbn, bookEntity);

        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }

    @Override
    public List<BookEntity> findAll() {
        logger.info("findAll books service called");

        List<BookEntity> books = new ArrayList<>();
        bookRepository.findAll().forEach(books::add);
        return books;
    }

    @Override
    public List<BookEntity> findAll(Pageable pageable) {
        logger.info("findAll books service with pagination called");

        return bookRepository.findAll(pageable).getContent();
    }

    @Override
    public Optional<BookEntity> findOne(String isbn) {
        logger.info("findOne book service called");
        logger.debug("findOne with isbn: {}", isbn);

        return bookRepository.findById(isbn);
    }

    @Override
    public boolean exists(String isbn) {
        logger.info("book exists service called");
        logger.debug("exists with isbn: {}", isbn);

        return bookRepository.existsById(isbn);
    }

    @Override
    public BookEntity partialUpdate(String isbn, BookEntity bookEntity) {
        logger.info("partialUpdate book service called");
        logger.debug("partialUpdate with isbn: {}, bookEntity: {}", isbn, bookEntity);

        bookEntity.setIsbn(isbn);
        return bookRepository.findById(isbn).map((existingBook) -> {
            logger.info("Requested book found, performing partial update");

            Optional.ofNullable(bookEntity.getTitle()).ifPresent(existingBook::setTitle);
            return bookRepository.save(existingBook);
        }).orElseThrow(() -> new RuntimeException("Book does not exist"));
    }

    @Override
    public void delete(String isbn) {
        logger.info("delete book service called");
        logger.debug("delete with isbn: {}", isbn);

        bookRepository.deleteById(isbn);
    }
}
