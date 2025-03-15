package com.mclods.restapp.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "book")
public class BookEntity {
    @Id
    private String isbn;

    private String title;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private AuthorEntity authorEntity;

    public BookEntity() {  }

    public BookEntity(String isbn, String title, AuthorEntity authorEntity) {
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

    public static BookEntityBuilder builder() {
        return new BookEntityBuilder();
    }

    public static class BookEntityBuilder {
        private String isbn, title;
        private AuthorEntity authorEntity;

        public BookEntityBuilder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public BookEntityBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BookEntityBuilder author(AuthorEntity authorEntity) {
            this.authorEntity = authorEntity;
            return this;
        }

        public BookEntity build() {
            return new BookEntity(isbn, title, authorEntity);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        if(!(obj instanceof BookEntity bookEntityObj)) {
            return false;
        }

        return isbn.equals(bookEntityObj.getIsbn()) &&
                title.equals(bookEntityObj.getTitle()) &&
                authorEntity.equals(bookEntityObj.getAuthor());
    }

    @Override
    public String toString() {
        return String.format("Book(isbn = %s, title = %s, author = %s)", isbn, title, authorEntity);
    }
}
