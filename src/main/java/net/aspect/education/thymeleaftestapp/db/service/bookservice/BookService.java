package net.aspect.education.thymeleaftestapp.db.service.bookservice;

import net.aspect.education.thymeleaftestapp.db.dto.BookDTO;
import net.aspect.education.thymeleaftestapp.db.entity.Book;

import java.util.List;

public interface BookService {
    List<BookDTO> getAllBook();
    BookDTO getBookById(int id);
    Book saveBook(BookDTO newBook);
    void deleteBookById(int id);
}
