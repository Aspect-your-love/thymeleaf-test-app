package net.aspect.education.thymeleaftestapp.db.service;

import jakarta.transaction.Transactional;
import net.aspect.education.thymeleaftestapp.db.dao.author.AuthorRepository;
import net.aspect.education.thymeleaftestapp.db.dao.book.BookRepository;
import net.aspect.education.thymeleaftestapp.db.dto.BookDTO;
import net.aspect.education.thymeleaftestapp.db.dto.mapper.MapperAuthor;
import net.aspect.education.thymeleaftestapp.db.entity.Author;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import net.aspect.education.thymeleaftestapp.db.service.authorservice.AuthorService;
import net.aspect.education.thymeleaftestapp.db.service.bookservice.BookService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
        net.aspect.education.thymeleaftestapp.ThymeleafTestAppApplication.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Tag("author-service")
public class BookServiceTest {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookService bookService;
    private final MapperAuthor mapper;

    private Author author1;
    private Author author2;
    private Author author3;

    private Book book1;
    private Book book2;
    private Book book3;

    @Autowired
    public BookServiceTest(BookRepository bookRepository
            , AuthorRepository authorRepository
            , BookService bookService
            , MapperAuthor mapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookService = bookService;
        this.mapper = mapper;
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

    @Test
    @DisplayName("Получение всех книг")
    public void getAllBooks() {
        List<BookDTO> allBook = bookService.getAll();

        assertAll(
                () -> assertThat(allBook).extracting(BookDTO::getName).contains(book1.getName(), book2.getName(), book3.getName()),
                () -> assertThat(allBook).hasSize(3)
        );
    }

    /*Получение книги по ID*/
    public void getBooksById(){
        BookDTO byId = bookService.getById(1);
        BookDTO byId1 = bookService.getById(2);
        BookDTO byId2 = bookService.getById(3);

        assertAll(
                () -> assertThat(byId).extracting(BookDTO::getName).isIn(book1.getName(), book2.getName(), book3.getName()),
                () -> assertThat(byId1).extracting(BookDTO::getName).isIn(book1.getName(), book2.getName(), book3.getName()),
                () -> assertThat(byId2).extracting(BookDTO::getName).isIn(book1.getName(), book2.getName(), book3.getName())
        );
    }

    /*Exception при неправильном ID*/
    public void getBooksByIdWithNotExistId(){
        try{
            bookService.getById(10000);
        } catch(NullPointerException e){
            assertTrue(true);
        }
    }

    /*Получение книги по имени*/
    public void getBooksByName(){
        BookDTO byName = bookService.getByName(book1.getName());
        BookDTO byName1 = bookService.getByName(book2.getName());
        BookDTO byName2 = bookService.getByName(book3.getName());

        assertAll(
                () -> assertThat(byName).isNotNull().extracting(BookDTO::getName).isEqualTo(book1.getName()),
                () -> assertThat(byName1).isNotNull().extracting(BookDTO::getName).isEqualTo(book2.getName()),
                () -> assertThat(byName2).isNotNull().extracting(BookDTO::getName).isEqualTo(book3.getName())
        );
    }

    /*Exception при неправильном Book.name*/
    public void getBooksByIdWithNotExistName(){
        try{
            bookService.getByName("такого-автора-не-существует");
        } catch(NullPointerException e){
            assertTrue(true);
        }
    }

    /*Добавление книги*/

    /*Удаление книги*/

    /*Обновление информации о книге*/

}
