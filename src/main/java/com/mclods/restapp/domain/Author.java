package com.mclods.restapp.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Short age;

    private String name;

    public Author() {  }

    public Author(String name, Short age) {
        this.name = name;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public Short getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void setAge(Short age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static AuthorBuilder builder() {
        return new AuthorBuilder();
    }

    public static class AuthorBuilder {
        private Short age;
        private String name;

        public AuthorBuilder age(Short age) {
            this.age = age;
            return this;
        }

        public AuthorBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Author build() {
            return new Author(name, age);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        if(!(obj instanceof Author authorObj)) {
            return false;
        }

        return id.equals(authorObj.getId()) &&
                age.equals(authorObj.getAge()) &&
                name.equals(authorObj.getName());
    }

    @Override
    public String toString() {
        return String.format("Author(id = %d, age = %d, name = %s)", id, age, name);
    }
}
