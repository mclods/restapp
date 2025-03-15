package com.mclods.restapp.services;

import com.mclods.restapp.domain.entities.AuthorEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthorService {
    AuthorEntity createAuthor(AuthorEntity authorEntity);
}
