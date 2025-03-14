package com.mclods.restapp;

import com.mclods.restapp.domain.Author;
import com.mclods.restapp.domain.Book;

public class TestDataUtils {
    public static Author testAuthorA() {
        return Author.builder()
                .age((short)44)
                .name("Dennis Levi")
                .build();
    }

    public static Author testAuthorB() {
        return Author.builder()
                .name("Bob Dylan")
                .age((short)45)
                .build();
    }

    public static Author testAuthorC() {
        return Author.builder()
                .name("Jeremy Reiner")
                .age((short)56)
                .build();
    }

    public static Book testBookA(Author author) {
        return Book.builder()
                .isbn("978-1-2345-6789-0")
                .title("Dark Soul")
                .author(author)
                .build();
    }

    public static Book testBookB(Author author) {
        return Book.builder()
                .isbn("978-9-2022-6589-9")
                .title("The Whispering Shadows")
                .author(author)
                .build();
    }

    public static Book testBookC(Author author) {
        return Book.builder()
                .isbn("978-3-3921-5711-8")
                .title("Echoes of the Forgotten Realm")
                .author(author)
                .build();
    }
}
