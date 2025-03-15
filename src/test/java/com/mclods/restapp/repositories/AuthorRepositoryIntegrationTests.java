package com.mclods.restapp.repositories;

import com.mclods.restapp.TestDataUtils;
import com.mclods.restapp.domain.entities.AuthorEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@SpringBootTest
@Transactional
public class AuthorRepositoryIntegrationTests {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorRepositoryIntegrationTests(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Test
    @DisplayName("Test author can be created and recalled")
    void testAuthorCanBeCreatedAndRecalled() {
        AuthorEntity authorEntity = TestDataUtils.testAuthorA();

        authorRepository.save(authorEntity);
        Optional<AuthorEntity> result = authorRepository.findById(authorEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get())
                .extracting(AuthorEntity::getName, AuthorEntity::getAge)
                .containsExactly("Dennis Levi", (short)44);
    }

    @Test
    @DisplayName("Test multiple authors can be created and recalled")
    void testMultipleAuthorsCanBeCreatedAndRecalled() {
        AuthorEntity authorEntityA = TestDataUtils.testAuthorA();
        AuthorEntity authorEntityB = TestDataUtils.testAuthorB();
        AuthorEntity authorEntityC = TestDataUtils.testAuthorC();

        authorRepository.save(authorEntityA);
        authorRepository.save(authorEntityB);
        authorRepository.save(authorEntityC);
        Iterable<AuthorEntity> results = authorRepository.findAll();
        assertThat(results).hasSize(3);
        assertThat(results)
                .extracting(AuthorEntity::getName, AuthorEntity::getAge)
                .containsExactly(
                        tuple("Dennis Levi", (short)44),
                        tuple("Bob Dylan", (short)45),
                        tuple("Jeremy Reiner", (short)56)
                );
    }

    @Test
    @DisplayName("Test author can be updated")
    void testAuthorCanBeUpdated() {
        AuthorEntity authorEntity = TestDataUtils.testAuthorA();
        authorRepository.save(authorEntity);

        authorEntity.setName("Max Payne");
        authorRepository.save(authorEntity);

        Optional<AuthorEntity> result = authorRepository.findById(authorEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get())
                .extracting(AuthorEntity::getName, AuthorEntity::getAge)
                .containsExactly("Max Payne", (short)44);
    }

    @Test
    @DisplayName("Test author can be deleted")
    void testAuthorCanBeDeleted() {
        AuthorEntity authorEntity = TestDataUtils.testAuthorA();
        authorRepository.save(authorEntity);

        authorRepository.delete(authorEntity);
        Optional<AuthorEntity> result = authorRepository.findById(authorEntity.getId());
        assertThat(result).isEmpty();
    }
}
