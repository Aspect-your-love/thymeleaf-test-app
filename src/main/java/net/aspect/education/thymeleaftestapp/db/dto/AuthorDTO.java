package net.aspect.education.thymeleaftestapp.db.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


public class AuthorDTO {
    private String name;
    private List<String> bookList;

    public AuthorDTO(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getBookList() {
        return bookList;
    }

    public void setBookList(List<String> bookList) {
        this.bookList = bookList;
    }

    @Override
    public String toString() {
        return "AuthorDTO[" +
                "name='" + name + '\'' +
                ']';
    }
}
