package com.mclods.restapp.controllers;

import com.mclods.restapp.domain.dto.AuthorDto;
import com.mclods.restapp.domain.entities.AuthorEntity;
import com.mclods.restapp.mappers.Mapper;
import com.mclods.restapp.services.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class AuthorController {

    private final AuthorService authorService;
    private final Mapper<AuthorEntity, AuthorDto> authorMapper;
    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "/authors")
    ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        logger.info("createAuthor endpoint called");
        logger.debug("Received authorDto: {}", authorDto);

        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity savedAuthorEntity = authorService.save(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/authors")
    List<AuthorDto> findAllAuthors() {
        logger.info("findAllAuthors endpoint called");

        List<AuthorDto> authors = new ArrayList<>();
        authorService.findAll().forEach((foundAuthor) ->
                authors.add(authorMapper.mapTo(foundAuthor)));
        return authors;
    }

    @GetMapping(path = "/authors/{id}")
    ResponseEntity<AuthorDto> findAuthor(@PathVariable("id") Integer id) {
        logger.info("findAuthor endpoint called");
        logger.debug("Received id: {}", id);

        Optional<AuthorEntity> author = authorService.findOne(id);
        return author.map((foundAuthor) -> {
            logger.info("Requested author found");

            AuthorDto authorDto = authorMapper.mapTo(foundAuthor);
            return new ResponseEntity<>(authorDto, HttpStatus.OK);
        }).orElseGet(() -> {
            logger.info("Requested author not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

    @PutMapping(path = "/authors/{id}")
    ResponseEntity<AuthorDto> fullUpdateAuthor(
            @PathVariable("id") Integer id,
            @RequestBody AuthorDto authorDto
    ) {
        logger.info("fullUpdateAuthor endpoint called");
        logger.debug("Received id: {}, authorDto: {}", id, authorDto);

        if(!authorService.exists(id)) {
            logger.info("Requested author not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AuthorEntity updatedAuthor = authorService.save(id, authorMapper.mapFrom(authorDto));
        return new ResponseEntity<>(authorMapper.mapTo(updatedAuthor), HttpStatus.OK);
    }

    @PatchMapping(path = "/authors/{id}")
    ResponseEntity<AuthorDto> partialUpdateAuthor(
            @PathVariable("id") Integer id,
            @RequestBody AuthorDto authorDto
    ) {
        logger.info("partialUpdateAuthor endpoint called");
        logger.debug("Received id: {}, authorDto: {}", id, authorDto);

        if(!authorService.exists(id)) {
            logger.info("Requested author not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AuthorEntity updatedAuthor = authorService.partialUpdate(id, authorMapper.mapFrom(authorDto));
        return new ResponseEntity<>(authorMapper.mapTo(updatedAuthor), HttpStatus.OK);
    }

    @DeleteMapping(path = "/authors/{id}")
    ResponseEntity<AuthorDto> deleteAuthor(@PathVariable("id") Integer id) {
        logger.info("deleteAuthor endpoint called");
        logger.debug("Received id: {}", id);

        if(!authorService.exists(id)) {
            logger.info("Requested author not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
