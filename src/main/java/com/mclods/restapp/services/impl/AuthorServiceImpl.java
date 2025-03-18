package com.mclods.restapp.services.impl;

import com.mclods.restapp.domain.entities.AuthorEntity;
import com.mclods.restapp.repositories.AuthorRepository;
import com.mclods.restapp.services.AuthorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

    @Override
    public List<AuthorEntity> findAll() {
        List<AuthorEntity> authors = new ArrayList<>();
        authorRepository.findAll().forEach(authors::add);
        return authors;
    }
}
