package net.aspect.education.thymeleaftestapp.db.service.bookservice;

import net.aspect.education.thymeleaftestapp.db.dto.BookDTO;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Override
    public List<BookDTO> getAll() {
        return List.of();
    }

    @Override
    public BookDTO getById(int i) {
        return null;
    }

    @Override
    public BookDTO getByName(String name) {
        return null;
    }

    @Override
    public BookDTO addBook(BookDTO currentBook) {
        return null;
    }

    @Override
    public void deleteBook(int i) {

    }

    @Override
    public Book updateBook(Book book) {
        return null;
    }
}