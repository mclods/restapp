package com.mclods.restapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mclods.restapp.TestDataUtils;
import com.mclods.restapp.domain.dto.BookDto;
import com.mclods.restapp.domain.entities.BookEntity;
import com.mclods.restapp.repositories.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final BookRepository bookRepository;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, BookRepository bookRepository) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.bookRepository = bookRepository;
    }

    @Test
    @DisplayName("Test create book succeeds with status code 201")
    void testCreateBookSucceedsWithStatusCode201() throws Exception {
        BookDto bookDto = TestDataUtils.testBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/999-111-222")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    @DisplayName("Test create book succeeds and returns saved book")
    void testCreateBookSucceedsAndReturnsSavedBook() throws Exception {
        BookDto bookDto = TestDataUtils.testBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/999-111-222")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("999-111-222")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Dark Soul")
        );
    }

    @Test
    @DisplayName("Test find all books succeeds with status code 200")
    void testFindAllBooksSucceedsWithStatusCode200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    @DisplayName("Test find all books succeeds and returns all books")
    void testFindAllBooksSucceedsAndReturnsAllBooks() throws Exception {
        BookEntity bookEntityA = TestDataUtils.testBookA(null);
        BookEntity bookEntityB = TestDataUtils.testBookB(null);
        bookRepository.save(bookEntityA);
        bookRepository.save(bookEntityB);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value("978-1-2345-6789-0")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value("Dark Soul")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].isbn").value("978-9-2022-6589-9")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].title").value("The Whispering Shadows")
        );
    }

    @Test
    @DisplayName("Test find one book succeeds with status code 200 Ok")
    void testFindOneBookSucceedsWithStatusCode200Ok() throws Exception {
        BookEntity bookEntity = TestDataUtils.testBookA(null);
        bookRepository.save(bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/978-1-2345-6789-0")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    @DisplayName("Test find one book fails with status code 404 Not Found")
    void testFindOneBookFailsWithStatusCode404NotFound() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    @DisplayName("Test find one book succeeds and returns the book")
    void testFindOneBookSucceedsAndReturnsTheBook() throws Exception {
        BookEntity bookEntity = TestDataUtils.testBookA(null);
        bookRepository.save(bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/978-1-2345-6789-0")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("978-1-2345-6789-0")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Dark Soul")
        );
    }

    @Test
    @DisplayName("Test full update book succeeds with status code 200")
    void testFullUpdateBookSucceedsWithStatusCode200() throws Exception {
        BookEntity savedBook = TestDataUtils.testBookA(null);
        bookRepository.save(savedBook);

        BookDto bookDto = TestDataUtils.testBookDtoB(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put(String.format("/books/%s", savedBook.getIsbn()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    @DisplayName("Test full update book succeeds and returns the updated book")
    void testFullUpdateBookSucceedsAndReturnsTheUpdatedBook() throws Exception {
        BookEntity savedBook = TestDataUtils.testBookA(null);
        bookRepository.save(savedBook);

        BookDto bookDto = TestDataUtils.testBookDtoB(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put(String.format("/books/%s", savedBook.getIsbn()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("978-1-2345-6789-0")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("The Whispering Shadows")
        );
    }
}
