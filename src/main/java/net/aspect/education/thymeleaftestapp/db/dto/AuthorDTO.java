package net.aspect.education.thymeleaftestapp.db.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthorDTO {
    private String name;

    public AuthorDTO(String name){
        this.name = name;
    }

}
