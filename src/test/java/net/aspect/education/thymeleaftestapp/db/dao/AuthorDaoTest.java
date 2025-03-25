package net.aspect.education.thymeleaftestapp.db.dao;

import jakarta.transaction.Transactional;
import net.aspect.education.thymeleaftestapp.db.dao.author.AuthorRepository;
import net.aspect.education.thymeleaftestapp.db.dao.book.BookRepository;
import net.aspect.education.thymeleaftestapp.db.dto.BookDTO;
import net.aspect.education.thymeleaftestapp.db.dto.mapper.MapperBookWithoutAuthor;
import net.aspect.education.thymeleaftestapp.db.entity.Author;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.security.AuthorizationAuditListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={
        net.aspect.education.thymeleaftestapp.ThymeleafTestAppApplication.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorDaoTest {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final MapperBookWithoutAuthor mapperBook;
    /*Написать в BeforeAll запись всех сущностей*/

    private Author author1;
    private Author author2;
    private Author author3;

    private Book book1;
    private Book book2;
    private Book book3;
    /*
    @Autowired
    private AuthorizationAuditListener authorizationAuditListener;*/

    @Autowired
    public AuthorDaoTest(BookRepository bookRepository, AuthorRepository authorRepository, MapperBookWithoutAuthor mapperBook) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.mapperBook = mapperBook;
    }

    @BeforeEach
    @Transactional
    public void saveEntity(){
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
    }



    /*
    * TODO: написать тесты:
    *  1 - Получение всех авторов из репозитория.
    *  2 - Получение всех книг автора
    *  3 - Добавление автора и книг к нему
    *  4 - Удаление автора
    *  5 - Обновление автора*/

    /**
     * Обнуляет все ссылки*/
    @AfterEach
    public void nullableBooksAndAuthors(){
        author1 = null;
        author2 = null;
        author3 = null;

        book1 = null;
        book2 = null;
        book3 = null;
    }

    /// Получение всех авторов
    @Transactional
    @Test
    public void getAuthors(){
        List<Author> authorsList = authorRepository.findAll();
        authorsList.forEach(author -> System.out.println(author.getName()));

        assertThat(authorsList).contains(author1, author2, author3);

    }

    public void getAuthorsById(){

    }

    @Transactional
    @Test
    public void addAuthors(){
        List<Author> authorInDb1 = authorRepository.getAuthorByName(author1.getName());
        List<Author> authorInDb2 = authorRepository.getAuthorByName(author2.getName());
        List<Author> authorInDb3 = authorRepository.getAuthorByName(author3.getName());

        assertAll(
                () -> assertThat(authorInDb1).contains(author1),
                () -> assertThat(authorInDb2).contains(author2),
                () -> assertThat(authorInDb3).contains(author3)
        );
    }

    @Test
    @Transactional
    public void getBooksToAuthor(){
        Book book1Get = bookRepository.getBookByName(book1.getName());

        System.out.println(book1Get.getId() + " :id");
        book1Get.getAuthors().forEach(author -> System.out.print(author.getName() + " -- "));
        System.out.println('\n');

        Assertions.assertTrue(true);
    }


}
