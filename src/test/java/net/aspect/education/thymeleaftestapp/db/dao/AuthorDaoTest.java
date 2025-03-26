package net.aspect.education.thymeleaftestapp.db.dao;

import jakarta.transaction.Transactional;
import net.aspect.education.thymeleaftestapp.db.dao.author.AuthorRepository;
import net.aspect.education.thymeleaftestapp.db.dao.book.BookRepository;
import net.aspect.education.thymeleaftestapp.db.entity.Author;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
        net.aspect.education.thymeleaftestapp.ThymeleafTestAppApplication.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
        author1.addBookToAuthor(book1);
        book1.addAuthorToBook(author2);
        author2.addBookToAuthor(book1);

        book2 = new Book();
        book2.setName("Book-2");
        book2.setYear(1976);
        book2.setFilePath("/file.path2");
        book2.addAuthorToBook(author1);
        author1.addBookToAuthor(book2);
        book2.addAuthorToBook(author3);
        author3.addBookToAuthor(book2);

        book3 = new Book();
        book3.setName("Book-3");
        book3.setYear(1972);
        book3.setFilePath("/file.path2");
        book3.addAuthorToBook(author2);
        author2.addBookToAuthor(book3);

        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        System.out.println("************************************");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("************************************");
    }



    /*
     * TODO: написать тесты:
     *  2 - Получение всех книг автора
     *  3 - Добавление автора и книг к нему
     *  4 - Удаление автора
     *  5 - Обновление автора*/

    /// Получение всех авторов
    @Transactional
    @Test
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
    public void getAuthorsById() {
        Optional<Author> currentAuthor1 = authorRepository.findById(1);
        Optional<Author> currentAuthor2 = authorRepository.findById(2);
        Optional<Author> currentAuthor3 = authorRepository.findById(3);

        System.out.printf(
                """
                        Current Author 1: %s. Current author ID: %d
                        Current Author 2: %s. Current author ID: %d
                        Current Author 3: %s. Current author ID: %d
                        
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

        /*
        // Нет гарантий того, что у нас будет получено по ID
        Author byId1 = authorRepository.findById(1).get();
        Author byId2 = authorRepository.findById(2).get();
        Author byId3 = authorRepository.findById(3).get();

        assertAll(
                () -> assertThat(byId1.getBooks().stream().map(Book::getName)).contains(book1.getName(), book2.getName()),
                () -> assertThat(byId2.getBooks().stream().map(Book::getName)).contains(book1.getName(), book3.getName()),
                () -> assertThat(byId3.getBooks().stream().map(Book::getName)).contains(book2.getName())
        );*/
    }


}
