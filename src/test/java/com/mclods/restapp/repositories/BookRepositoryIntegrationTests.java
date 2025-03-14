package com.mclods.restapp.repositories;

import com.mclods.restapp.TestDataUtils;
import com.mclods.restapp.domain.Author;
import com.mclods.restapp.domain.Book;
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
        Author author = TestDataUtils.testAuthorA();
        Book book = TestDataUtils.testBookA(author);

        bookRepository.save(book);
        Optional<Book> result = bookRepository.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get())
                .extracting(
                        Book::getIsbn,
                        Book::getTitle,
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
        Author authorA = TestDataUtils.testAuthorA();
        Author authorB = TestDataUtils.testAuthorB();
        Author authorC = TestDataUtils.testAuthorC();

        Book bookA = TestDataUtils.testBookA(authorA);
        Book bookB = TestDataUtils.testBookB(authorB);
        Book bookC = TestDataUtils.testBookC(authorC);

        bookRepository.save(bookA);
        bookRepository.save(bookB);
        bookRepository.save(bookC);

        Iterable<Book> results = bookRepository.findAll();
        assertThat(results).hasSize(3);
        assertThat(results)
                .extracting(
                        Book::getIsbn,
                        Book::getTitle,
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
        Author author = TestDataUtils.testAuthorA();
        Book book = TestDataUtils.testBookA(author);
        bookRepository.save(book);

        book.setTitle("Goblin's Fire");
        bookRepository.save(book);

        Optional<Book> result = bookRepository.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).extracting(
                Book::getIsbn,
                Book::getTitle,
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
        Author author = TestDataUtils.testAuthorA();
        Book book = TestDataUtils.testBookA(author);
        bookRepository.save(book);

        bookRepository.delete(book);
        Optional<Book> result = bookRepository.findById(book.getIsbn());
        assertThat(result).isEmpty();
    }

}
