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
    ResponseEntity<BookDto> createBook(@PathVariable String isbn, @RequestBody BookDto bookDto) {
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBookEntity = bookService.create(isbn, bookEntity);
        return new ResponseEntity<>(bookMapper.mapTo(savedBookEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/books")
    List<BookDto> findAllBooks() {
        List<BookDto> books = new ArrayList<>();
        bookService.findAll().forEach((n) -> books.add(bookMapper.mapTo(n)));
        return books;
    }
}
