package com.mclods.restapp.services;

import com.mclods.restapp.domain.entities.AuthorEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    AuthorEntity save(AuthorEntity authorEntity);
    AuthorEntity save(Integer id, AuthorEntity authorEntity);
    List<AuthorEntity> findAll();
    List<AuthorEntity> findAll(Pageable pageable);
    Optional<AuthorEntity> findOne(Integer id);
    boolean exists(Integer id);
    AuthorEntity partialUpdate(Integer id, AuthorEntity authorEntity);
    void delete(Integer id);
}
