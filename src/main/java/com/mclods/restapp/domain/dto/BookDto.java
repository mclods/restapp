package com.mclods.restapp.domain.dto;

public class BookDto {
    private String isbn;

    private String title;

    private AuthorDto authorDto;

    public BookDto() {  }

    public BookDto(String isbn, String title, AuthorDto authorDto) {
        this.isbn = isbn;
        this.title = title;
        this.authorDto = authorDto;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public AuthorDto getAuthor() {
        return authorDto;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(AuthorDto authorDto) {
        this.authorDto = authorDto;
    }

    public static BookDtoBuilder builder() {
        return new BookDtoBuilder();
    }

    public static class BookDtoBuilder {
        private String isbn, title;
        private AuthorDto authorDto;

        public BookDtoBuilder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public BookDtoBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BookDtoBuilder author(AuthorDto authorDto) {
            this.authorDto = authorDto;
            return this;
        }

        public BookDto build() {
            return new BookDto(isbn, title, authorDto);
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
                authorDto.equals(bookDtoObj.getAuthor());
    }

    @Override
    public String toString() {
        return String.format("BookDto(isbn = %s, title = %s, author = %s)", isbn, title, authorDto);
    }
}
