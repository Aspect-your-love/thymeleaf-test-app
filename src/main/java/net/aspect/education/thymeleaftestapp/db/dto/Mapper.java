package net.aspect.education.thymeleaftestapp.db.dto;

import net.aspect.education.thymeleaftestapp.db.entity.Author;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapper {

    /**Вроде бы преобразует Book в DTO*/
    public BookDTO toBookDTO(Book book) {
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

    // TODO: Проверить данное преобразование
    /**
     * Преобразует BookDTO в book. Вроде бы.*/
    public Book toBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setName(bookDTO.getName());
        book.setYear(bookDTO.getYear());
        book.setFilePath(bookDTO.getFilePath());

        bookDTO
                .getAuthorsName()
                .forEach(
                        author -> book.addAuthorToBook(new Author(author))
                );

        return book;
    }
}
