package net.aspect.education.thymeleaftestapp.db.dao;

import jakarta.transaction.Transactional;
import net.aspect.education.thymeleaftestapp.db.dao.author.AuthorRepository;
import net.aspect.education.thymeleaftestapp.db.dao.book.BookRepository;
import net.aspect.education.thymeleaftestapp.db.dto.mapper.MapperAuthor;
import net.aspect.education.thymeleaftestapp.db.entity.Author;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
        net.aspect.education.thymeleaftestapp.ThymeleafTestAppApplication.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Tag("author-dao")
public class AuthorDaoTest {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    private Author author1;
    private Author author2;
    private Author author3;

    private Book book1;
    private Book book2;
    private Book book3;

    @Autowired
    public AuthorDaoTest(BookRepository bookRepository
            , AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
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

    @AfterEach
    public void afterEach() {
        System.out.println("************************************");
    }

    /// Получение всех авторов
    @Transactional
    @Test
    @DisplayName("Получение всех авторов")
    public void getAllAuthors() {
        author1 = authorRepository.getAuthorByName(author1.getName()).getFirst();
        author2 = authorRepository.getAuthorByName(author2.getName()).getFirst();
        author2 = authorRepository.getAuthorByName(author3.getName()).getFirst();

        List<Author> authorsList = authorRepository.findAll();

        assertThat(authorsList).contains(author1, author2, author3);
    }

    /**
     * Получение автора по ID
     */
    @Test
    @Transactional
    @DisplayName("Получение автора по ID")
    public void getAuthorsById() {
        Optional<Author> currentAuthor1 = authorRepository.findById(1);
        Optional<Author> currentAuthor2 = authorRepository.findById(2);
        Optional<Author> currentAuthor3 = authorRepository.findById(3);

        System.out.printf(
                """
                        Current Author 1: %s. Current author ID: %d
                        Current Author 2: %s. Current author ID: %d
                        Current Author 3: %s. Current author ID: %d                       \s
                        %n"""
                , currentAuthor1.get().getName()
                , currentAuthor1.get().getId()
                , currentAuthor2.get().getName()
                , currentAuthor2.get().getId()
                , currentAuthor3.get().getName()
                , currentAuthor3.get().getId()
        );
        assertAll(
                () -> assertThat(currentAuthor1).isPresent(),
                () -> assertThat(currentAuthor2).isPresent(),
                () -> assertThat(currentAuthor3).isPresent()
        );
    }

    /**
     * Проверяет наличие авторов в книге
     */
    @Test
    @Transactional
    @DisplayName("Получение автора из книги")
    public void getAuthorsInBook() {
        Book book1Get = bookRepository.getBookByName(book1.getName());
        Book book2Get = bookRepository.getBookByName(book2.getName());
        Book book3Get = bookRepository.getBookByName(book3.getName());

        assertAll(
                () -> assertThat(book1Get.getAuthors()).contains(author1, author2),
                () -> assertThat(book2Get.getAuthors()).contains(author1, author3),
                () -> assertThat(book3Get.getAuthors()).contains(author2)
        );
    }

    /**
     * Получение всех книг автора*/
    @Test
    @Transactional
    @DisplayName("Получение книг по имени автора")
    public void getBooksByAuthor() {
        Author byNameA1 = authorRepository.getAuthorByName(author1.getName()).getFirst();
        Author byNameA2 = authorRepository.getAuthorByName(author2.getName()).getFirst();
        Author byNameA3 = authorRepository.getAuthorByName(author3.getName()).getFirst();

        assertAll(
                () -> {
                    List<String> books = byNameA1.getBooks().stream().map(Book::getName).toList();
                    assertThat(books).contains(book1.getName(), book2.getName());
                },
                () -> {
                    List<String> books = byNameA2.getBooks().stream().map(Book::getName).toList();
                    assertThat(books).contains(book1.getName(), book3.getName());
                },
                () -> {
                    List<String> books = byNameA3.getBooks().stream().map(Book::getName).toList();
                    assertThat(books).contains(book2.getName());
                }
        );
    }

    /**
     * Проверяет добавление автора и связанных с ним
     * книг*/
    @Transactional
    @Test
    @DisplayName("Добавление автора вместе с книгой")
    public void addAuthorWithBook(){
        Author currentAuthor = new Author("Horus");

        Book currentBook1 = new Book("Text-1", 2012, "/path-life.txt", new HashSet<>());
        currentBook1.addAuthorToBook(currentAuthor);
        Book currentBook2 = new Book("Text-2", 2015, "/path-life2.txt", new HashSet<>());
        currentBook2.addAuthorToBook(currentAuthor);
        Book currentBook3 = new Book("Text-3", 2019, "/path-life3.txt", new HashSet<>());
        currentBook3.addAuthorToBook(currentAuthor);


        Author returnAuthor = authorRepository.save(currentAuthor);

        assertAll(
                () -> assertThat(returnAuthor).isNotNull(),
                () -> assertThat(returnAuthor.getBooks())
                        .isNotEmpty().map(Book::getName)
                        .contains(currentBook1.getName()
                                , currentBook2.getName()
                                , currentBook3.getName())
        );
    }

    /**
     * Изменение существующего автора*/
    @Transactional
    @Test
    @DisplayName("Обновление автора")
    public void updateAuthor(){
        Author currentAuthor = authorRepository.getAuthorByName(author1.getName()).getFirst();
        currentAuthor.setName("Author From test UPDATE methode");
        Author authorAfterUpdate = authorRepository.save(currentAuthor);

        assertAll(
                () ->assertThat(currentAuthor.getId()).isEqualTo(authorAfterUpdate.getId()),
                () ->assertThat(currentAuthor.getName()).isEqualTo(authorAfterUpdate.getName())
        );
    }

    /**
     * Удаление автора из БД*/
    @Test
    @Transactional
    @DisplayName("Удаление автора")
    public void deleteAuthor(){
        Author authorCurrent = authorRepository.findById(2).get();

        authorCurrent.getBooks().forEach(book -> book.removeAuthor(authorCurrent));
        authorCurrent.removeBookList();

        authorRepository.delete(authorCurrent);
        authorRepository.findAll().forEach(s -> System.out.println(s.getName()));

        assertThat(authorRepository.findAll().size()).isEqualTo(2);
    }
}
