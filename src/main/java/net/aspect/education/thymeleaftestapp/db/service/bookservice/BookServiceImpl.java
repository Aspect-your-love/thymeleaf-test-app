package net.aspect.education.thymeleaftestapp.db.service.bookservice;

import jakarta.transaction.Transactional;
import net.aspect.education.thymeleaftestapp.db.dao.author.AuthorRepository;
import net.aspect.education.thymeleaftestapp.db.dao.book.BookRepository;
import net.aspect.education.thymeleaftestapp.db.dto.BookDTO;
import net.aspect.education.thymeleaftestapp.db.dto.mapper.MapperAuthor;
import net.aspect.education.thymeleaftestapp.db.dto.mapper.MapperBookWithoutAuthor;
import net.aspect.education.thymeleaftestapp.db.entity.Author;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final MapperBookWithoutAuthor mapperBook;
    private final MapperAuthor mapperAuthor;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository
            , AuthorRepository authorRepository
            , MapperBookWithoutAuthor mapperBook
            , MapperAuthor mapperAuthor) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.mapperBook = mapperBook;
        this.mapperAuthor = mapperAuthor;
    }

    @Override
    @Transactional
    public List<BookDTO> getAll() {

        return bookRepository
                .findAll()
                .stream()
                .map(mapperBook::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public BookDTO getById(int i) {
        Optional<Book> byId = bookRepository.findById(i);

        if (byId.isEmpty()) throw new NullPointerException("Книги с указанным ID не найдено");

        return mapperBook.toDTO(byId.get());
    }

    @Override
    @Transactional
    public BookDTO getByName(String name) {
        List<Book> byName = bookRepository.findByName(name);

        if (byName.isEmpty()) throw new NullPointerException("Указанных книг с заданным именем не существует");

        return mapperBook.toDTO(byName.getFirst());
    }

    @Override
    @Transactional
    public BookDTO addBook(BookDTO currentBook) {
        Book book = mapperBook.toEntity(currentBook);

        /*Для каждого автора, который есть у currentBook,
         * проводим проверку на то, существует ли автор с таким
         * именем в БД. Если нет, то сохраняем автора и добавляем его к книге*/
        if (currentBook.getAuthorsName() != null) {
            currentBook.getAuthorsName().forEach(
                    authorName -> {
                        List<Author> checkAuthor = authorRepository.getAuthorByName(authorName);

                        if (checkAuthor.isEmpty()) {
                            Author newAuthor = new Author(authorName);
                            newAuthor.addBookToAuthor(book);
                            authorRepository.save(newAuthor);
                        }

                        Author existAuthor = checkAuthor.getFirst();
                        existAuthor.addBookToAuthor(book);
                        authorRepository.save(existAuthor);
                    }
            );
        }

        return mapperBook.toDTO(bookRepository.save(book));
    }

    @Override
    @Transactional
    public void deleteBook(int i) {
        Optional<Book> byId = bookRepository.findById(i);

        if (byId.isEmpty()) throw new NullPointerException("Не найдена книга с таким ID для удаления");

        bookRepository.delete(byId.get());
    }

    @Override
    @Transactional
    public BookDTO updateBook(BookDTO book) {
        Optional<Book> byId = bookRepository.findById(book.getId());

        if (byId.isEmpty()) throw new NullPointerException("Книги с таким ID нет. Обновления не будет");

        Book newBook = mapperBook.toEntity(book);
        newBook.setAuthors(byId.get().getAuthors());

        bookRepository.save(newBook);
        return mapperBook.toDTO(newBook);
    }
}