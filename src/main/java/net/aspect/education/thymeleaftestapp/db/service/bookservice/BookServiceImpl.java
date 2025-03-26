package net.aspect.education.thymeleaftestapp.db.service.bookservice;

import jakarta.transaction.Transactional;
import net.aspect.education.thymeleaftestapp.db.dao.author.AuthorRepository;
import net.aspect.education.thymeleaftestapp.db.dao.book.BookRepository;
import net.aspect.education.thymeleaftestapp.db.dto.BookDTO;
import net.aspect.education.thymeleaftestapp.db.dto.mapper.MapperBookWithoutAuthor;
import net.aspect.education.thymeleaftestapp.db.entity.Author;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final MapperBookWithoutAuthor mapperBookWithoutAuthor;

    /*Внедрение зависимостей происходит через конструктор*/
    @Autowired
    public BookServiceImpl(BookRepository bookRepository
            , AuthorRepository authorRepository
            , MapperBookWithoutAuthor mapperBookWithoutAuthor) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.mapperBookWithoutAuthor = mapperBookWithoutAuthor;
    }

    /**
     * Получение всех книг из БД
     */
    @Override
    @Transactional
    public List<BookDTO> getAllBook() {
        List<Book> allBook = bookRepository.findAll();
        System.out.println("Были найдены книги: " + allBook);

        List<BookDTO> bookDTOList = allBook
                        .stream()
                        .map(book -> mapperBookWithoutAuthor.toDTO(book))
                        .toList();

        return bookDTOList;
    }

    /**
     * Получение книги по ID
     */
    @Override
    @Transactional
    public BookDTO getBookById(int id) {
        Optional<Book> getBookOptional = bookRepository.findById(id);

        if (getBookOptional.isEmpty())
            throw new NullPointerException(String.format("Object with %d not exist.", id));


        return mapperBookWithoutAuthor.toDTO(getBookOptional.get());
    }

    /**Сохранение книги через DTO*/
    @Override
    @Transactional
    public Book saveBook(BookDTO newBook) {

        // TODO: маппинг переделать
        List<Author> authorsObj = newBook
                .getAuthorsName()
                .stream()
                .distinct()
                .map(
                        authorName -> {
                            List<Author> currentAuthor = authorRepository.getAuthorByName(authorName);

                            if (currentAuthor != null) {
                                return currentAuthor.getFirst();
                            } else {
                                return new Author(authorName);
                            }
                        }
                )
                .toList();

        Book resultBook = mapperBookWithoutAuthor.toEntity(newBook);
        authorsObj.forEach(resultBook::addAuthorToBook);

        bookRepository.save(resultBook);

        return resultBook;
    }

    /**
     * Удаление книги из БД
     */
    @Override
    @Transactional
    public void deleteBookById(int id) {
        Book book = mapperBookWithoutAuthor.toEntity(this.getBookById(id));

        if (book == null) throw new NullPointerException("Нет такой книги.");

        bookRepository.deleteById(id);
    }
}