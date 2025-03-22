package com.mclods.restapp.controllers;

import com.mclods.restapp.domain.dto.BookDto;
import com.mclods.restapp.domain.entities.BookEntity;
import com.mclods.restapp.mappers.Mapper;
import com.mclods.restapp.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {
    private final BookService bookService;
    private final Mapper<BookEntity, BookDto> bookMapper;

    public BookController(BookService bookService, Mapper<BookEntity, BookDto> bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PutMapping(path = "/books/{isbn}")
    ResponseEntity<BookDto> createUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {
        boolean bookExists = bookService.exists(isbn);
        BookEntity savedUpdatedBook = bookService.save(isbn, bookMapper.mapFrom(bookDto));

        if(bookExists) {
            return new ResponseEntity<>(bookMapper.mapTo(savedUpdatedBook), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(bookMapper.mapTo(savedUpdatedBook), HttpStatus.CREATED);
        }
    }

    @GetMapping(path = "/books")
    List<BookDto> findAllBooks() {
        List<BookDto> books = new ArrayList<>();
        bookService.findAll().forEach((foundBook) ->
                books.add(bookMapper.mapTo(foundBook)));
        return books;
    }

    @GetMapping(path = "/books/{isbn}")
    ResponseEntity<BookDto> findBook(@PathVariable("isbn") String isbn) {
        return bookService.findOne(isbn).map((foundBook) -> {
            BookDto bookDto = bookMapper.mapTo(foundBook);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/books/{isbn}")
    ResponseEntity<BookDto> partialUpdateBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto
    ) {
      if(!bookService.exists(isbn)) {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      BookEntity updatedBook = bookService.partialUpdate(isbn, bookMapper.mapFrom(bookDto));
      return new ResponseEntity<>(bookMapper.mapTo(updatedBook), HttpStatus.OK);
    }
}
