package com.mclods.restapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mclods.restapp.TestDataUtils;
import com.mclods.restapp.domain.dto.BookDto;
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

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
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
}
