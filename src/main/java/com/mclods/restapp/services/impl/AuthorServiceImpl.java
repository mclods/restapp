package com.mclods.restapp.services.impl;

import com.mclods.restapp.domain.entities.AuthorEntity;
import com.mclods.restapp.repositories.AuthorRepository;
import com.mclods.restapp.services.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class);

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
        logger.info("save author service called");
        logger.debug("save with authorEntity: {}", authorEntity);

        return authorRepository.save(authorEntity);
    }

    @Override
    public AuthorEntity save(Integer id, AuthorEntity authorEntity) {
        logger.info("update author service called");
        logger.debug("save with id: {}, authorEntity: {}", id, authorEntity);

        authorEntity.setId(id);
        return authorRepository.save(authorEntity);
    }

    @Override
    public List<AuthorEntity> findAll() {
        logger.info("findAll authors service called");

        List<AuthorEntity> authors = new ArrayList<>();
        authorRepository.findAll().forEach(authors::add);
        return authors;
    }

    @Override
    public List<AuthorEntity> findAll(Pageable pageable) {
        logger.info("findAll authors service with pagination called");

        return authorRepository.findAll(pageable).getContent();
    }

    @Override
    public Optional<AuthorEntity> findOne(Integer id) {
        logger.info("findOne author service called");
        logger.debug("findOne with id: {}", id);

        return authorRepository.findById(id);
    }

    @Override
    public boolean exists(Integer id) {
        logger.info("author exists service called");
        logger.debug("exists with id: {}", id);

        return authorRepository.existsById(id);
    }

    @Override
    public AuthorEntity partialUpdate(Integer id, AuthorEntity authorEntity) {
        logger.info("partialUpdate author service called");
        logger.debug("partialUpdate with id: {}, authorEntity: {}", id, authorEntity);

        authorEntity.setId(id);

        return authorRepository.findById(id).map((existingAuthor) -> {
            logger.info("Requested author found, performing partial update");

            Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);
            Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor::setName);
            return authorRepository.save(existingAuthor);
        }).orElseThrow(() -> new RuntimeException("Author does not exist"));
    }

    @Override
    public void delete(Integer id) {
        logger.info("delete author service called");
        logger.debug("delete with id: {}", id);

        authorRepository.deleteById(id);
    }
}
