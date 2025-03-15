package com.mclods.restapp.repositories;

import com.mclods.restapp.TestDataUtils;
import com.mclods.restapp.domain.entities.AuthorEntity;
import com.mclods.restapp.domain.entities.BookEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@Transactional
public class BookRepositoryIntegrationTests {
    private final BookRepository bookRepository;

    @Autowired
    public BookRepositoryIntegrationTests(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Test
    @DisplayName("Test book can be created and recalled")
    void testBookCanBeCreatedAndRecalled() {
        AuthorEntity authorEntity = TestDataUtils.testAuthorA();
        BookEntity bookEntity = TestDataUtils.testBookA(authorEntity);

        bookRepository.save(bookEntity);
        Optional<BookEntity> result = bookRepository.findById(bookEntity.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get())
                .extracting(
                        BookEntity::getIsbn,
                        BookEntity::getTitle,
                        (n) -> n.getAuthor().getName(),
                        (n) -> n.getAuthor().getAge()
                ).containsExactly(
                        "978-1-2345-6789-0",
                        "Dark Soul",
                        "Dennis Levi",
                        (short)44
                );
    }

    @Test
    @DisplayName("Test multiple books can be created and recalled")
    void testMultipleBookCanBeCreatedAndRecalled() {
        AuthorEntity authorEntityA = TestDataUtils.testAuthorA();
        AuthorEntity authorEntityB = TestDataUtils.testAuthorB();
        AuthorEntity authorEntityC = TestDataUtils.testAuthorC();

        BookEntity bookEntityA = TestDataUtils.testBookA(authorEntityA);
        BookEntity bookEntityB = TestDataUtils.testBookB(authorEntityB);
        BookEntity bookEntityC = TestDataUtils.testBookC(authorEntityC);

        bookRepository.save(bookEntityA);
        bookRepository.save(bookEntityB);
        bookRepository.save(bookEntityC);

        Iterable<BookEntity> results = bookRepository.findAll();
        assertThat(results).hasSize(3);
        assertThat(results)
                .extracting(
                        BookEntity::getIsbn,
                        BookEntity::getTitle,
                        (n) -> n.getAuthor().getName(),
                        (n) -> n.getAuthor().getAge()
                ).containsExactly(
                        tuple(
                                "978-1-2345-6789-0",
                                "Dark Soul",
                                "Dennis Levi",
                                (short)44
                        ),
                        tuple(
                                "978-9-2022-6589-9",
                                "The Whispering Shadows",
                                "Bob Dylan",
                                (short)45
                        ),
                        tuple(
                                "978-3-3921-5711-8",
                                "Echoes of the Forgotten Realm",
                                "Jeremy Reiner",
                                (short)56
                        )
                );
    }

    @Test
    @DisplayName("Test book can be updated")
    void testBookCanBeUpdated() {
        AuthorEntity authorEntity = TestDataUtils.testAuthorA();
        BookEntity bookEntity = TestDataUtils.testBookA(authorEntity);
        bookRepository.save(bookEntity);

        bookEntity.setTitle("Goblin's Fire");
        bookRepository.save(bookEntity);

        Optional<BookEntity> result = bookRepository.findById(bookEntity.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).extracting(
                BookEntity::getIsbn,
                BookEntity::getTitle,
                (n) -> n.getAuthor().getName(),
                (n) -> n.getAuthor().getAge()
        ).containsExactly(
                "978-1-2345-6789-0",
                "Goblin's Fire",
                "Dennis Levi",
                (short)44
        );
    }

    @Test
    @DisplayName("Test book can be deleted")
    void testBookCanBeDeleted() {
        AuthorEntity authorEntity = TestDataUtils.testAuthorA();
        BookEntity bookEntity = TestDataUtils.testBookA(authorEntity);
        bookRepository.save(bookEntity);

        bookRepository.delete(bookEntity);
        Optional<BookEntity> result = bookRepository.findById(bookEntity.getIsbn());
        assertThat(result).isEmpty();
    }

}
