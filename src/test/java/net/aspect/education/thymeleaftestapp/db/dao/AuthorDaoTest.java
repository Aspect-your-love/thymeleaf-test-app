package net.aspect.education.thymeleaftestapp.db.dao;

import jakarta.transaction.Transactional;
import net.aspect.education.thymeleaftestapp.configuration.H2JpaConfig;
import net.aspect.education.thymeleaftestapp.db.dao.author.AuthorRepository;
import net.aspect.education.thymeleaftestapp.db.dao.book.BookRepository;
import net.aspect.education.thymeleaftestapp.db.entity.Author;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={
        net.aspect.education.thymeleaftestapp.ThymeleafTestAppApplication.class})
public class AuthorDaoTest {


    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorDaoTest(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    // Просто добавил тест.
    @Transactional
    @Test
    public void getAuthors(){
        System.out.println("Test");
        Book book = new Book();
        book.setYear(1900);
        book.setName("book-for-faggots");
        book.setFilePath("/path.");
        Author author = new Author("author");
        book.addAuthorToBook(author);

        Author author2 = authorRepository.save(author);

        Assertions.assertEquals(author.getName(), author2.getName());
    }
}
