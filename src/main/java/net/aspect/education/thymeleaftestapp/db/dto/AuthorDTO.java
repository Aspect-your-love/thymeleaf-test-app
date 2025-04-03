package net.aspect.education.thymeleaftestapp.db.dto;

import java.util.Set;


public class AuthorDTO {
    /// ID в бд
    private int id;
    /// Имя автора
    private String name;
    /// Названия книг, которые написаны автором
    private Set<String> bookList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getBookList() {
        return bookList;
    }

    public void setBookList(Set<String> bookList) {
        this.bookList = bookList;
    }

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "name='" + name + '\'' +
                ", bookList=" + bookList +
                '}';
    }
}
