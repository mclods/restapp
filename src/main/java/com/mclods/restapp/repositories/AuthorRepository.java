package com.mclods.restapp.repositories;

import com.mclods.restapp.domain.entities.AuthorEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends
        CrudRepository<AuthorEntity, Integer>,
        PagingAndSortingRepository<AuthorEntity, Integer> {
}
