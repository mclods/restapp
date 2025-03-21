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
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedUpdatedBook = bookService.save(isbn, bookEntity);

        if(bookExists) {
            return new ResponseEntity<>(bookMapper.mapTo(savedUpdatedBook), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(bookMapper.mapTo(savedUpdatedBook), HttpStatus.CREATED);
        }
    }

    @GetMapping(path = "/books")
    List<BookDto> findAllBooks() {
        List<BookDto> books = new ArrayList<>();
        bookService.findAll().forEach((bookEntity) -> books.add(bookMapper.mapTo(bookEntity)));
        return books;
    }

    @GetMapping(path = "/books/{isbn}")
    ResponseEntity<BookDto> findBook(@PathVariable("isbn") String isbn) {
        return bookService.findOne(isbn).map((bookEntity) -> {
            BookDto bookDto = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
