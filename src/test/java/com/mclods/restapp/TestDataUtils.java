package com.mclods.restapp;

import com.mclods.restapp.domain.dto.AuthorDto;
import com.mclods.restapp.domain.dto.BookDto;
import com.mclods.restapp.domain.entities.AuthorEntity;
import com.mclods.restapp.domain.entities.BookEntity;

public class TestDataUtils {
    public static AuthorEntity testAuthorA() {
        return AuthorEntity.builder()
                .age((short)44)
                .name("Dennis Levi")
                .build();
    }

    public static AuthorDto testAuthorDtoA() {
        return AuthorDto.builder()
                .id(1)
                .age((short)44)
                .name("Dennis Levi")
                .build();
    }

    public static AuthorEntity testAuthorB() {
        return AuthorEntity.builder()
                .name("Bob Dylan")
                .age((short)45)
                .build();
    }

    public static AuthorDto testAuthorDtoB() {
        return AuthorDto.builder()
                .id(2)
                .name("Bob Dylan")
                .age((short)45)
                .build();
    }

    public static AuthorEntity testAuthorC() {
        return AuthorEntity.builder()
                .name("Jeremy Reiner")
                .age((short)56)
                .build();
    }

    public static BookEntity testBookA(AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("978-1-2345-6789-0")
                .title("Dark Soul")
                .author(authorEntity)
                .build();
    }

    public static BookDto testBookDtoA(AuthorDto authorDto) {
        return BookDto.builder()
                .isbn("978-1-2345-6789-0")
                .title("Dark Soul")
                .author(authorDto)
                .build();
    }

    public static BookEntity testBookB(AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("978-9-2022-6589-9")
                .title("The Whispering Shadows")
                .author(authorEntity)
                .build();
    }

    public static BookDto testBookDtoB(AuthorDto authorDto) {
        return BookDto.builder()
                .isbn("978-9-2022-6589-9")
                .title("The Whispering Shadows")
                .author(authorDto)
                .build();
    }

    public static BookEntity testBookC(AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("978-3-3921-5711-8")
                .title("Echoes of the Forgotten Realm")
                .author(authorEntity)
                .build();
    }
}
