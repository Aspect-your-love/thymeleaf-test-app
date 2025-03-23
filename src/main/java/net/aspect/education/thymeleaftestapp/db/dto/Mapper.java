package net.aspect.education.thymeleaftestapp.db.dto;

import net.aspect.education.thymeleaftestapp.db.entity.Author;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    /**
     * Преобразует BookDTO в book.
     * Но не преобразует строковые имена авторов в объекты Author.\
     * Вместо этого устанавливает пустой лист*/
    public Book toBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setName(bookDTO.getName());
        book.setYear(bookDTO.getYear());
        book.setFilePath(bookDTO.getFilePath());
        book.setAuthors(new ArrayList<>());

        return book;
    }
}
