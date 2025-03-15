package com.mclods.restapp.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "author")
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Short age;

    private String name;

    public AuthorEntity() {  }

    public AuthorEntity(String name, Short age) {
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

    public static AuthorEntityBuilder builder() {
        return new AuthorEntityBuilder();
    }

    public static class AuthorEntityBuilder {
        private Short age;
        private String name;

        public AuthorEntityBuilder age(Short age) {
            this.age = age;
            return this;
        }

        public AuthorEntityBuilder name(String name) {
            this.name = name;
            return this;
        }

        public AuthorEntity build() {
            return new AuthorEntity(name, age);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        if(!(obj instanceof AuthorEntity authorEntityObj)) {
            return false;
        }

        return id.equals(authorEntityObj.getId()) &&
                age.equals(authorEntityObj.getAge()) &&
                name.equals(authorEntityObj.getName());
    }

    @Override
    public String toString() {
        return String.format("Author(id = %d, age = %d, name = %s)", id, age, name);
    }
}
