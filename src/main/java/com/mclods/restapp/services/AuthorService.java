package com.mclods.restapp.services;

import com.mclods.restapp.domain.entities.AuthorEntity;

import java.util.List;

public interface AuthorService {
    AuthorEntity createAuthor(AuthorEntity authorEntity);
    List<AuthorEntity> findAll();
}
