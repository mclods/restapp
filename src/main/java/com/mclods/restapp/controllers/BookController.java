package com.mclods.restapp.controllers;

import com.mclods.restapp.domain.dto.BookDto;
import com.mclods.restapp.domain.entities.BookEntity;
import com.mclods.restapp.mappers.Mapper;
import com.mclods.restapp.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {
    private final BookService bookService;
    private final Mapper<BookEntity, BookDto> bookMapper;
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    public BookController(BookService bookService, Mapper<BookEntity, BookDto> bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PutMapping(path = "/books/{isbn}")
    ResponseEntity<BookDto> createUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {
        logger.info("createUpdateBook endpoint called");
        logger.debug("Received isbn: {}, bookDto: {}", isbn, bookDto);

        boolean bookExists = bookService.exists(isbn);
        BookEntity savedUpdatedBook = bookService.save(isbn, bookMapper.mapFrom(bookDto));

        if(bookExists) {
            logger.info("Requested book found");
            return new ResponseEntity<>(bookMapper.mapTo(savedUpdatedBook), HttpStatus.OK);
        } else {
            logger.info("Requested book not found");
            return new ResponseEntity<>(bookMapper.mapTo(savedUpdatedBook), HttpStatus.CREATED);
        }
    }

    @GetMapping(path = "/books")
    List<BookDto> findAllBooks() {
        logger.info("findAllBooks endpoint called");

        List<BookDto> books = new ArrayList<>();
        bookService.findAll().forEach((foundBook) ->
                books.add(bookMapper.mapTo(foundBook)));
        return books;
    }

    @GetMapping(path = "/books/{isbn}")
    ResponseEntity<BookDto> findBook(@PathVariable("isbn") String isbn) {
        logger.info("findBook endpoint called");
        logger.debug("Received isbn: {}", isbn);

        return bookService.findOne(isbn).map((foundBook) -> {
            logger.info("Requested book found");

            BookDto bookDto = bookMapper.mapTo(foundBook);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElseGet(() -> {
            logger.info("Requested book not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

    @PatchMapping(path = "/books/{isbn}")
    ResponseEntity<BookDto> partialUpdateBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto
    ) {
      logger.info("partialUpdateBook endpoint called");
      logger.debug("Received isbn: {}, bookDto: {}", isbn, bookDto);

      if(!bookService.exists(isbn)) {
          logger.info("Requested book not found");
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      BookEntity updatedBook = bookService.partialUpdate(isbn, bookMapper.mapFrom(bookDto));
      return new ResponseEntity<>(bookMapper.mapTo(updatedBook), HttpStatus.OK);
    }

    @DeleteMapping(path = "/books/{isbn}")
    ResponseEntity<BookDto> deleteBook(@PathVariable("isbn") String isbn) {
        logger.info("deleteBook endpoint called");
        logger.debug("Received isbn: {}", isbn);

        if(!bookService.exists(isbn)) {
            logger.info("Requested book not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        bookService.delete(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
