package net.aspect.education.thymeleaftestapp.db.dao;

import jakarta.transaction.Transactional;
import net.aspect.education.thymeleaftestapp.ThymeleafTestAppApplication;
import net.aspect.education.thymeleaftestapp.db.dao.author.AuthorRepository;
import net.aspect.education.thymeleaftestapp.db.dao.book.BookRepository;
import net.aspect.education.thymeleaftestapp.db.dto.mapper.MapperAuthor;
import net.aspect.education.thymeleaftestapp.db.entity.Author;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("FieldCanBeLocal")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
        ThymeleafTestAppApplication.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDAOTest {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    private Author author1;
    private Author author2;
    private Author author3;

    private Book book1;
    private Book book2;
    private Book book3;
    @Autowired
    private MapperAuthor mapperAuthor;

    @Autowired
    public BookDAOTest(BookRepository bookRepository
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

    @Test
    @DisplayName("Получение всех книг из БД")
    @Transactional
    public void getAllBooks(){
        List<Book> all = bookRepository.findAll();
        List<String> list = all.stream().map(Book::getName).toList();
        assertAll(
                () -> assertThat(all).isNotEmpty(),
                () -> assertThat(list).contains(book1.getName(), book2.getName(), book3.getName())
        );
    }

    @Test
    @DisplayName("Получение книги по ID")
    @Transactional
    public void getBookById(){
        Optional<Book> byId1 = bookRepository.findById(1);
        Optional<Book> byId2 = bookRepository.findById(2);
        Optional<Book> byId3 = bookRepository.findById(3);

        assertAll(
                () -> assertThat(byId1).isPresent(),
                () -> assertThat(byId2).isPresent(),
                () -> assertThat(byId3).isPresent()
        );
    }

    @Test
    @DisplayName("Получение книги по имени")
    @Transactional
    public void getBookByName(){
        List<Book> byName = bookRepository.findByName(book1.getName());

        assertAll(
                () -> assertThat(byName).isNotEmpty().hasSize(1),
                () -> assertThat(byName.getFirst().getName()).isEqualTo(book1.getName())
        );
    }

    @Test
    @DisplayName("Добавление книги вместе с авторами")
    @Transactional
    public void addBook(){
        Book newBook = new Book("Shadow warriois", 1000, "filePath", null);
        Author newAuthor1 = new Author("Stasyan");
        Author newAuthor2 = new Author("Misha");

        newBook.addAuthorToBook(newAuthor1);
        newBook.addAuthorToBook(newAuthor2);

        Book addBookWithId = bookRepository.save(newBook);
        Optional<Book> returningBook = bookRepository.findById(addBookWithId.getId());

        if (!returningBook.isPresent()){
            throw new NullPointerException("Возвращаемое значение пустое");
        }

        Set<Author> authorSet = returningBook.get().getAuthors();
        assertAll(
                () -> assertThat(returningBook.get()).isEqualTo(newBook),
                () -> assertThat(returningBook.get().getName()).isEqualTo(newBook.getName()),
                () -> assertThat(returningBook.get().getAuthors()).contains(newAuthor1, newAuthor2)
        );
    }

    @Test
    @DisplayName("Обновление книги существующей в БД")
    @Transactional
    public void updateBook(){
        String newName = "Test Name";
        int year = 2043;

        Optional<Book> byId = bookRepository.findById(1);

        if(!byId.isPresent()) throw new NullPointerException("Вернулось пустое значение");
        Book retBook = byId.get();

        retBook.setYear(year);
        retBook.setName(newName);

        bookRepository.save(retBook);

        Optional<Book> byId1 = bookRepository.findById(1);

        if(!byId1.isPresent()) throw new NullPointerException("Вернулось пустое значение");
        Book retBook2 = byId1.get();

        assertAll(
                () -> assertThat(retBook2.getName()).isEqualTo(newName),
                () -> assertThat(retBook2.getYear()).isEqualTo(year)
        );

    }

    public void deleteBook(){
        Optional<Book> byId = bookRepository.findById(1);

        if(!byId.isPresent()) throw new NullPointerException("Вернулось пустое значение");

        Book testBook = byId.get();

        testBook.getAuthors().forEach(a -> a.removeBookAssociations(testBook));
        testBook.removeAuthorAssociations();

        bookRepository.delete(testBook);

        Optional<Book> returnBook = bookRepository.findById(1);

        assertThat(returnBook).isNotPresent();
    }

    @Test
    @DisplayName("Удаление автора из книги")
    @Transactional
    public void deleteAuthorFromBook(){
        List<Book> byName = bookRepository.findByName(book2.getName());

        Book retBook = byName.getFirst();
        retBook.removeAuthor(author3);
        List<Author> author = authorRepository.getAuthorByName(author3.getName());

        Author retAuthor = author.getFirst();
        retAuthor.removeBookAssociations(retBook);

        Author saveAuthor = authorRepository.save(retAuthor);
        Book saveBook = bookRepository.save(retBook);

        assertAll(
                () -> assertThat(saveBook.getAuthors()).hasSize(1),
                () -> assertThat(saveAuthor.getBooks()).hasSize(0)
        );
    }
}