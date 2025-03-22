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
    @DisplayName("Test create author succeeds and returns the saved author")
    void testCreateAuthorSucceedsAndReturnsTheSavedAuthor() throws Exception {
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
    @DisplayName("Test find all authors succeed and returns all the authors")
    void testFindAllAuthorsSucceedsAndReturnsAllTheAuthors() throws Exception {
        AuthorEntity savedAuthorA = TestDataUtils.testAuthorA();
        AuthorEntity savedAuthorB = TestDataUtils.testAuthorB();
        authorRepository.save(savedAuthorA);
        authorRepository.save(savedAuthorB);

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

    @Test
    @DisplayName("Test find one author succeeds with status code 200 OK when author exists")
    void testFindOneAuthorSucceedsWithStatusCode200OKWhenAuthorExists() throws Exception {
        AuthorEntity savedAuthor = TestDataUtils.testAuthorA();
        authorRepository.save(savedAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders.get(String.format("/authors/%d", savedAuthor.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    @DisplayName("Test find one author fails with status code 404 Not Found when author does not exist")
    void testFindOneAuthorSucceedsWithStatusCode404NotFoundWhenAuthorDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    @DisplayName("Test find one author succeeds and returns the author")
    void testFindOneAuthorSucceedsAndReturnsTheAuthor() throws Exception {
        AuthorEntity savedAuthor = TestDataUtils.testAuthorA();
        authorRepository.save(savedAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders.get(String.format("/authors/%d", savedAuthor.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Dennis Levi")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(44)
        );
    }

    @Test
    @DisplayName("Test full update author succeeds with status code 200 OK when author exists")
    void testFullUpdateAuthorSucceedsWithStatusCode200OKWhenAuthorExists() throws Exception {
        AuthorEntity savedAuthor = TestDataUtils.testAuthorA();
        authorRepository.save(savedAuthor);

        AuthorDto authorDto = TestDataUtils.testAuthorDtoB();
        String authorJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put(String.format("/authors/%d", savedAuthor.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    @DisplayName("Test full update author fails with status code 404 Not Found when author does not exist")
    void testFullUpdateAuthorFailsWithStatusCode404NotFoundWhenAuthorDoesNotExist() throws Exception {
        AuthorDto authorDto = TestDataUtils.testAuthorDtoA();
        String authorJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    @DisplayName("Test full update author succeeds and returns the updated author")
    void testFullUpdateAuthorSucceedsAndReturnsTheUpdatedAuthor() throws Exception {
        AuthorEntity savedAuthor = TestDataUtils.testAuthorA();
        authorRepository.save(savedAuthor);

        AuthorDto authorDto = TestDataUtils.testAuthorDtoB();
        String authorJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put(String.format("/authors/%d", savedAuthor.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Bob Dylan")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(45)
        );
    }

    @Test
    @DisplayName("Test partial update author succeeds with status code 200 OK when author exists")
    void testPartialUpdateAuthorSucceedsWithStatusCode200OKWhenAuthorExists() throws Exception {
        AuthorEntity savedAuthor = TestDataUtils.testAuthorA();
        authorRepository.save(savedAuthor);

        AuthorDto authorDto = AuthorDto.builder()
                .id(savedAuthor.getId())
                .name("Kumar Amit")
                .build();
        String authorJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch(String.format("/authors/%d", savedAuthor.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    @DisplayName("Test partial update author fails with status code 404 Not Found when author does not exist")
    void testPartialUpdateAuthorFailsWithStatusCode404NotFoundWhenAuthorDoesNotExist() throws Exception {
        AuthorDto authorDto = AuthorDto.builder()
                .id(999)
                .name("Kumar Amit")
                .build();
        String authorJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    @DisplayName("Test partial update succeeds and returns the updated author")
    void testPartialUpdateSucceedsAndReturnsTheUpdatedAuthor() throws Exception {
        AuthorEntity savedAuthor = TestDataUtils.testAuthorA();
        authorRepository.save(savedAuthor);

        AuthorDto authorDto = AuthorDto.builder()
                .id(savedAuthor.getId())
                .name("Kumar Amit")
                .build();
        String authorJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch(String.format("/authors/%d", savedAuthor.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Kumar Amit")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(44)
        );
    }
}
