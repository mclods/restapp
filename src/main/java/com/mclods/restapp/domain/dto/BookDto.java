package com.mclods.restapp.domain.dto;

import com.mclods.restapp.domain.entities.AuthorEntity;

public class BookDto {
    private String isbn;

    private String title;

    private AuthorEntity authorEntity;

    public BookDto() {  }

    public BookDto(String isbn, String title, AuthorEntity authorEntity) {
        this.isbn = isbn;
        this.title = title;
        this.authorEntity = authorEntity;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public AuthorEntity getAuthor() {
        return authorEntity;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(AuthorEntity authorEntity) {
        this.authorEntity = authorEntity;
    }

    public static BookDtoBuilder builder() {
        return new BookDtoBuilder();
    }

    public static class BookDtoBuilder {
        private String isbn, title;
        private AuthorEntity authorEntity;

        public BookDtoBuilder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public BookDtoBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BookDtoBuilder author(AuthorEntity authorEntity) {
            this.authorEntity = authorEntity;
            return this;
        }

        public BookDto build() {
            return new BookDto(isbn, title, authorEntity);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        if(!(obj instanceof BookDto bookDtoObj)) {
            return false;
        }

        return isbn.equals(bookDtoObj.getIsbn()) &&
                title.equals(bookDtoObj.getTitle()) &&
                authorEntity.equals(bookDtoObj.getAuthor());
    }

    @Override
    public String toString() {
        return String.format("BookDto(isbn = %s, title = %s, author = %s)", isbn, title, authorEntity);
    }
}
