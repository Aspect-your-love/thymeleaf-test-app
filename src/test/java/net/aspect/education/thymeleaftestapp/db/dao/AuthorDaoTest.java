package net.aspect.education.thymeleaftestapp.db.dao;

import jakarta.transaction.Transactional;
import net.aspect.education.thymeleaftestapp.db.dao.author.AuthorRepository;
import net.aspect.education.thymeleaftestapp.db.dao.book.BookRepository;
import net.aspect.education.thymeleaftestapp.db.entity.Author;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.security.AuthorizationAuditListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={
        net.aspect.education.thymeleaftestapp.ThymeleafTestAppApplication.class})
@SuppressWarnings({"unused", "hiding"})
public class AuthorDaoTest {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

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
    public AuthorDaoTest(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    /*
    * TODO: написать тесты:
    *  1 - Получение всех авторов из репозитория.
    *  2 - Получение всех книг автора
    *  3 - Добавление автора и книг к нему
    *  4 - Удаление автора
    *  5 - Обновление автора*/

    @BeforeEach
    public void addAuthorsAndBooksEntity(){
        author1 = new Author("author1");
        author2 = new Author("author2");
        author3 = new Author("author3");

        /* Добавить книги*/
    }

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

    // Просто добавил тест.
    @Transactional
    @Test
    @Disabled
    public void getAuthors(){

    }

    public void getAuthorsById(){

    }

    @Transactional
    @Test
    public void addAuthors(){
        authorRepository.save(author1);
        authorRepository.save(author2);
        authorRepository.save(author3);

        List<Author> authorInDb1 = authorRepository.getAuthorByName(author1.getName());
        List<Author> authorInDb2 = authorRepository.getAuthorByName(author2.getName());
        List<Author> authorInDb3 = authorRepository.getAuthorByName(author3.getName());

        Assertions.assertAll(
                () -> assertThat(authorInDb1).contains(author1),
                () -> assertThat(authorInDb2).contains(author2),
                () -> assertThat(authorInDb3).contains(author3)
        );
    }

    public void addBookToAuthors(){

    }


}
