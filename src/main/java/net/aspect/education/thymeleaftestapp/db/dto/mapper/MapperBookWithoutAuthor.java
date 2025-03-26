package net.aspect.education.thymeleaftestapp.db.dto.mapper;

import net.aspect.education.thymeleaftestapp.db.dto.BookDTO;
import net.aspect.education.thymeleaftestapp.db.entity.Author;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Мапер.
 * Служит для преобразования объекта Book в BookDTO.
 * Используется в основном в <b>@Service слое</b> */
@Component
public class MapperBookWithoutAuthor implements Mapper<Book, BookDTO> {

    /**
     * Преобразует Book в DTO.<br>
     * Также, преобразует авторов в список имён (String)*/
    public BookDTO toDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setName(book.getName());
        bookDTO.setYear(book.getYear());
        bookDTO.setFilePath(book.getFilePath());

        List<String> authors = book
                .getAuthors()
                .stream()
                .map(Author::getName)
                .toList();

        bookDTO.setAuthorsName(authors);

        return bookDTO;
    }

    /**
     * Преобразует BookDTO в book.<br>
     * Не преобразует строковые имена авторов в объекты Author.<br>*/
    public Book toEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setName(bookDTO.getName());
        book.setYear(bookDTO.getYear());
        book.setFilePath(bookDTO.getFilePath());

        return book;
    }
}
