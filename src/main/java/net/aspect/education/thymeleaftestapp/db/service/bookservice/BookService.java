package net.aspect.education.thymeleaftestapp.db.service.bookservice;

import net.aspect.education.thymeleaftestapp.db.dto.BookDTO;
import net.aspect.education.thymeleaftestapp.db.entity.Book;

import java.util.List;

public interface BookService {

    public List<BookDTO> getAll();

    BookDTO getById(int i);

    BookDTO getByName(String name);
}
