package net.aspect.education.thymeleaftestapp.db.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


public class AuthorDTO {
    private String name;

    public AuthorDTO(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AuthorDTO[" +
                "name='" + name + '\'' +
                ']';
    }
}
