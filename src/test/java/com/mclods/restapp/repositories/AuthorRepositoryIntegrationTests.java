package com.mclods.restapp.repositories;

import com.mclods.restapp.TestDataUtils;
import com.mclods.restapp.domain.Author;
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
        Author author = TestDataUtils.testAuthorA();

        authorRepository.save(author);
        Optional<Author> result = authorRepository.findById(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get())
                .extracting(Author::getName, Author::getAge)
                .containsExactly("Dennis Levi", (short)44);
    }

    @Test
    @DisplayName("Test multiple authors can be created and recalled")
    void testMultipleAuthorsCanBeCreatedAndRecalled() {
        Author authorA = TestDataUtils.testAuthorA();
        Author authorB = TestDataUtils.testAuthorB();
        Author authorC = TestDataUtils.testAuthorC();

        authorRepository.save(authorA);
        authorRepository.save(authorB);
        authorRepository.save(authorC);
        Iterable<Author> results = authorRepository.findAll();
        assertThat(results).hasSize(3);
        assertThat(results)
                .extracting(Author::getName, Author::getAge)
                .containsExactly(
                        tuple("Dennis Levi", (short)44),
                        tuple("Bob Dylan", (short)45),
                        tuple("Jeremy Reiner", (short)56)
                );
    }

    @Test
    @DisplayName("Test author can be updated")
    void testAuthorCanBeUpdated() {
        Author author = TestDataUtils.testAuthorA();
        authorRepository.save(author);

        author.setName("Max Payne");
        authorRepository.save(author);

        Optional<Author> result = authorRepository.findById(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get())
                .extracting(Author::getName, Author::getAge)
                .containsExactly("Max Payne", (short)44);
    }

    @Test
    @DisplayName("Test author can be deleted")
    void testAuthorCanBeDeleted() {
        Author author = TestDataUtils.testAuthorA();
        authorRepository.save(author);

        authorRepository.delete(author);
        Optional<Author> result = authorRepository.findById(author.getId());
        assertThat(result).isEmpty();
    }
}
