package com.mclods.restapp.domain.dto;

public class AuthorDto {
    private Integer id;

    private Short age;

    private String name;

    public AuthorDto() {  }

    public AuthorDto(Integer id, String name, Short age) {
        this.id = id;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAge(Short age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static AuthorDtoBuilder builder() {
        return new AuthorDtoBuilder();
    }

    public static class AuthorDtoBuilder {
        private Integer id;

        private Short age;

        private String name;

        public AuthorDtoBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public AuthorDtoBuilder age(Short age) {
            this.age = age;
            return this;
        }

        public AuthorDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public AuthorDto build() {
            return new AuthorDto(id, name, age);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        if(!(obj instanceof AuthorDto authorDtoObj)) {
            return false;
        }

        return id.equals(authorDtoObj.getId()) &&
                name.equals(authorDtoObj.getName()) &&
                age.equals(authorDtoObj.getAge());
    }

    @Override
    public String toString() {
        return String.format("AuthorDto(id = %d, age = %d, name = %s)", id, age, name);
    }
}
