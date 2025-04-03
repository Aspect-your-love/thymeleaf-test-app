package net.aspect.education.thymeleaftestapp.db.dto.mapper;

import net.aspect.education.thymeleaftestapp.db.dto.BookDTO;
import net.aspect.education.thymeleaftestapp.db.entity.Author;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

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
        bookDTO.setId(book.getId());
        bookDTO.setName(book.getName());
        bookDTO.setYear(book.getYear());
        bookDTO.setFilePath(book.getFilePath());

        if (book.getAuthors() != null) {
            Set<String> authors = book
                    .getAuthors()
                    .stream()
                    .map(Author::getName)
                    .collect(Collectors.toSet());

            bookDTO.setAuthorsName(authors);
        }
        return bookDTO;
    }

    /**
     * Преобразует BookDTO в book.<br>
     * Не преобразует строковые имена авторов в объекты Author.<br>*/
    public Book toEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setName(bookDTO.getName());
        book.setYear(bookDTO.getYear());
        book.setFilePath(bookDTO.getFilePath());

        return book;
    }
}
