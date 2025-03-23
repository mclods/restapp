package com.mclods.restapp.repositories;

import com.mclods.restapp.domain.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends
        CrudRepository<BookEntity, String>,
        PagingAndSortingRepository<BookEntity, String> {
}
