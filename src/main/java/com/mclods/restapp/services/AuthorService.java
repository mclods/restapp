package com.mclods.restapp.services;

import com.mclods.restapp.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    AuthorEntity create(AuthorEntity authorEntity);
    List<AuthorEntity> findAll();
    Optional<AuthorEntity> findOne(Integer id);
}
