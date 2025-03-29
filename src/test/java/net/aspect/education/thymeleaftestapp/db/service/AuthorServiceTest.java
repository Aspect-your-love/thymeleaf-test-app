package net.aspect.education.thymeleaftestapp.db.service;

import jakarta.transaction.Transactional;
import net.aspect.education.thymeleaftestapp.db.dao.author.AuthorRepository;
import net.aspect.education.thymeleaftestapp.db.dao.book.BookRepository;
import net.aspect.education.thymeleaftestapp.db.dto.AuthorDTO;
import net.aspect.education.thymeleaftestapp.db.entity.Author;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import net.aspect.education.thymeleaftestapp.db.service.authorservice.AuthorService;
import net.aspect.education.thymeleaftestapp.db.service.authorservice.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
        net.aspect.education.thymeleaftestapp.ThymeleafTestAppApplication.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Tag("author-dao")
public class AuthorServiceTest {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final AuthorService authorService;

    private Author author1;
    private Author author2;
    private Author author3;

    private Book book1;
    private Book book2;
    private Book book3;

    @Autowired
    public AuthorServiceTest(BookRepository bookRepository
            , AuthorRepository authorRepository
            , AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.authorService = authorService;
    }

    @BeforeEach
    @Transactional
    public void saveEntity() {
        author1 = new Author("Author-1");
        author2 = new Author("Author-2");
        author3 = new Author("Author-3");

        book1 = new Book();
        book1.setName("Book-1");
        book1.setYear(1970);
        book1.setFilePath("/file.path");
        book1.addAuthorToBook(author1);
        book1.addAuthorToBook(author2);

        book2 = new Book();
        book2.setName("Book-2");
        book2.setYear(1976);
        book2.setFilePath("/file.path2");
        book2.addAuthorToBook(author1);
        book2.addAuthorToBook(author3);

        book3 = new Book();
        book3.setName("Book-3");
        book3.setYear(1972);
        book3.setFilePath("/file.path2");
        book3.addAuthorToBook(author2);

        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        System.out.println("************************************");
    }


    @DisplayName("Получение всех авторов со списком книг")
    public void getAllAuthorsWithBook() {
        List<AuthorDTO> authorsList = authorService.getAll();


    }

    @DisplayName("Получение одного автора по ID")
    public void getAuthorById() {
        int idForTest = 1;
        Optional<AuthorDTO> byId = authorService.getById(idForTest);
    }

    @DisplayName("Получение автора по имени")
    public void getAuthorByName() {
        Optional<AuthorDTO> byName = authorService.getByName(author1.getName());
    }

    @DisplayName("Добавление автора с книгами")
    public void addAuthor() {
        AuthorDTO thisAuthor = new AuthorDTO();
        /*TODO: реализовать DTO*/
        authorService.addAuthor(thisAuthor);
    }

    @DisplayName("Удаление автора")
    public void deleteAuthor() {

        authorService.deleteAuthor(1);
    }

    @DisplayName("Обновление информации об авторе")
    public void updateAuthor() {
        AuthorDTO authorDTOTest;
        authorService.updateAuthor(authorDTOTest);
    }
}
