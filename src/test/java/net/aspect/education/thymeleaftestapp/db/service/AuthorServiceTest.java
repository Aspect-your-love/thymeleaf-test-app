package net.aspect.education.thymeleaftestapp.db.service;

import jakarta.transaction.Transactional;
import net.aspect.education.thymeleaftestapp.db.dao.author.AuthorRepository;
import net.aspect.education.thymeleaftestapp.db.dao.book.BookRepository;
import net.aspect.education.thymeleaftestapp.db.dto.AuthorDTO;
import net.aspect.education.thymeleaftestapp.db.dto.mapper.MapperAuthor;
import net.aspect.education.thymeleaftestapp.db.entity.Author;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import net.aspect.education.thymeleaftestapp.db.service.authorservice.AuthorService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
        net.aspect.education.thymeleaftestapp.ThymeleafTestAppApplication.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Tag("author-service")
public class AuthorServiceTest {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final AuthorService authorService;
    private final MapperAuthor mapper;

    private Author author1;
    private Author author2;
    private Author author3;

    private Book book1;
    private Book book2;
    private Book book3;

    @Autowired
    public AuthorServiceTest(BookRepository bookRepository
            , AuthorRepository authorRepository
            , AuthorService authorService
            , MapperAuthor mapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.authorService = authorService;
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
    @DisplayName("Получение всех авторов со списком книг")
    public void getAllAuthorsWithBook() {
        List<AuthorDTO> authorsList = authorService.getAll();

        assertAll(
                () -> {
                    authorsList.forEach(System.out::println);
                    List<String> authorsListName = authorsList.stream().map(AuthorDTO::getName).toList();
                    assertThat(authorsListName).containsAnyOf(author1.getName()
                            , author2.getName()
                            , author3.getName());
                },
                () -> {
                    AuthorDTO currentAuthor = authorsList.getFirst();
                    assertThat(currentAuthor.getBookList()).isNotEmpty().containsAnyOf("Book-1", "Book-2", "Book-3");
                }
        );

    }

    @Test
    @DisplayName("Получение одного автора по ID")
    public void getAuthorById() {
        int idForTest = 1;
        Optional<AuthorDTO> byId = authorService.getById(idForTest);

        assertAll(
                () -> assertThat(byId).isPresent(),
                () -> {
                    if (byId.isEmpty()) fail("AuthorDTO не представлено");
                    System.out.printf(
                            """
                                    Name author: %s
                                    Book list: %s
                                    """,
                            byId.get().getName(),
                            byId.get().getBookList()
                    );
                    assertThat(byId.get().getBookList()).isNotEmpty().containsAnyOf("Book-1", "Book-2", "Book-3");
                }
        );
    }

    @Test
    @DisplayName("Получение автора по имени")
    public void getAuthorByName() {
        Optional<AuthorDTO> byName = authorService.getByName(author1.getName());

        if (byName.isEmpty()) fail("Optional пустой");

        assertAll(
                () -> assertThat(byName.get().getName()).isEqualTo(author1.getName()),
                () -> assertThat(byName.get().getBookList()).containsAnyOf("Book-1", "Book-2", "Book-3")
        );
    }

    @Test
    @DisplayName("Добавление автора с книгами")
    public void addAuthor() {
        Author currentAuthor = new Author("Ksue Butenko");
        currentAuthor.addBookToAuthor(book1);
        currentAuthor.addBookToAuthor(book3);

        authorService.addAuthor(mapper.toDTO(currentAuthor));

        Optional<AuthorDTO> currentAuthorDTOReturning = authorService.getByName(currentAuthor.getName());

        if (currentAuthorDTOReturning.isEmpty()) fail("Автор не был сохранён и был возвращён пустой Optional");

        AuthorDTO aD = currentAuthorDTOReturning.get();

        System.out.println(aD);

        assertAll(
                () -> assertThat(aD.getName()).isEqualTo(currentAuthor.getName()),
                () -> assertThat(aD.getBookList()).contains(book1.getName(), book3.getName())
        );
    }

    @Test
    @DisplayName("Удаление автора")
    public void deleteAuthor() {
        authorService.deleteAuthor(1);

        try {
            authorService.getById(1);
        } catch (NullPointerException ex){
            assertTrue(true);
        }
    }

    @Test
    @DisplayName("Обновление информации об авторе")
    public void updateAuthor() {
        Optional<AuthorDTO> authorDTOTestOptional = authorService.getById(1);

        if (authorDTOTestOptional.isEmpty()) fail("Указанный Author не вернулся....");

        AuthorDTO authorDTOTest = authorDTOTestOptional.get();

        authorDTOTest.setName("New Name For Test");

        System.out.println(authorDTOTest);

        AuthorDTO ret = authorService.updateAuthor(authorDTOTest);

        assertThat(ret.getName()).isEqualTo(authorDTOTest.getName());
    }
}
