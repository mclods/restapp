package com.mclods.restapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mclods.restapp.TestDataUtils;
import com.mclods.restapp.domain.dto.AuthorDto;
import com.mclods.restapp.domain.entities.AuthorEntity;
import com.mclods.restapp.repositories.AuthorRepository;
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
public class AuthorControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc, AuthorRepository authorRepository) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.authorRepository = authorRepository;
    }

    @Test
    @DisplayName("Test create author succeeds with status code 201")
    void testCreateAuthorSucceedsWithStatusCode201() throws Exception {
        AuthorDto authorDto = TestDataUtils.testAuthorDtoA();
        authorDto.setId(null);
        String authorJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    @DisplayName("Test create author succeeds and returns saved author")
    void testCreateAuthorSucceedsAndReturnsSavedAuthor() throws Exception {
        AuthorDto authorDto = TestDataUtils.testAuthorDtoA();
        authorDto.setId(null);
        String authorJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Dennis Levi")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(44)
        );
    }

    @Test
    @DisplayName("Test find all authors succeeds with status code 200")
    void testFindAllAuthorsSucceedsWithStatusCode200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    @DisplayName("Test find all authors succeed and returns all authors")
    void testFindAllAuthorsSucceedsAndReturnsAllAuthors() throws Exception {
        AuthorEntity authorEntityA = TestDataUtils.testAuthorA();
        AuthorEntity authorEntityB = TestDataUtils.testAuthorB();
        authorRepository.save(authorEntityA);
        authorRepository.save(authorEntityB);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("Dennis Levi")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(44)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].name").value("Bob Dylan")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].age").value(45)
        );
    }
}
