package net.aspect.education.thymeleaftestapp.db.service.authorservice;

import net.aspect.education.thymeleaftestapp.db.entity.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAllAuthor();
    Author getAuthorById(int id);
    void saveOrUpdateAuthor(Author author);
    void deleteAuthorById(int id);
    Author getAuthorByName(String name);
}
