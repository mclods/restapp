package com.mclods.restapp.services;

import com.mclods.restapp.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    AuthorEntity save(AuthorEntity authorEntity);
    AuthorEntity save(Integer id, AuthorEntity authorEntity);
    List<AuthorEntity> findAll();
    Optional<AuthorEntity> findOne(Integer id);
    boolean exists(Integer id);
    AuthorEntity partialUpdate(Integer id, AuthorEntity authorEntity);
}
