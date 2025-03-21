package net.aspect.education.thymeleaftestapp.db.service.bookservice;

import jakarta.transaction.Transactional;
import net.aspect.education.thymeleaftestapp.db.dao.author.AuthorRepository;
import net.aspect.education.thymeleaftestapp.db.dao.book.BookRepository;
import net.aspect.education.thymeleaftestapp.db.dto.BookDTO;
import net.aspect.education.thymeleaftestapp.db.dto.Mapper;
import net.aspect.education.thymeleaftestapp.db.entity.Author;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import org.hibernate.sql.model.ast.builder.CollectionRowDeleteByUpdateSetNullBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    private Mapper mapper = new Mapper();

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository){
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }
    /**
     * Получение всех книг из БД
     */
    @Override
    @Transactional
    public List<BookDTO> getAllBook() {
        List<Book> allBook = bookRepository.findAll();
        System.out.println("Были найдены книги: " + allBook);

        List<BookDTO> bookDTOList =
                allBook
                        .stream()
                        .map(book -> mapper.toBookDTO(book))
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


        return mapper.toBookDTO(getBookOptional.get());
    }


    // TODO: не работает метод
    @Override
    @Transactional
    public Book saveBook(BookDTO newBook) {
        /*Получили список строк имён авторов. Теперь, нам надо проверить,
        * есть ли в БД авторы с таким именем.*/
        List<String> authorsName = newBook.getAuthorsName();
        if(authorsName.isEmpty()) throw new NullPointerException();

        /*Находим автора по имени. Если такой есть, то получим его ID и установим его существующему автору
        * если нет, то добавляем автора без ID.*/

        List<Author> authorsObj = authorsName
                .stream()
                .map(name -> {
                    Author author = authorRepository.getAuthorByName(name);

                    if(author != null) return author;
                    else return new Author(name);
                })
                .toList();

        Book resultBook = mapper.toBook(newBook);

        for (Author author : authorsObj) {
            if(author.getId() == 0){
                authorRepository.save(author);
            }
        }

        resultBook.setAuthors(authorsObj);
        bookRepository.save(resultBook);

        /*Теперь к каждому автору добавляем книгу.*/
        for(Author a : resultBook.getAuthors()){
            a.addBookToAuthor(resultBook);
        }


        return resultBook;
    }

    /**
     * Удаление книги из БД
     */
    @Override
    @Transactional
    public void deleteBookById(int id) {
        Book book = mapper.toBook(this.getBookById(id));

        if (book == null) throw new NullPointerException("Нет такой книги.");

        bookRepository.deleteById(id);
    }
}