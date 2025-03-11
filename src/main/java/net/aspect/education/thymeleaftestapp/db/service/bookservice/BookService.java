package net.aspect.education.thymeleaftestapp.db.service.bookservice;

import net.aspect.education.thymeleaftestapp.db.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBook();
    Book getBookById(int id);
    Book saveOrUpdateBook(Book newBook);
    void deleteBookById(int id);
}
