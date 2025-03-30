package net.aspect.education.thymeleaftestapp.db.service.authorservice;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.aspect.education.thymeleaftestapp.db.dao.author.AuthorRepository;
import net.aspect.education.thymeleaftestapp.db.dao.book.BookRepository;
import net.aspect.education.thymeleaftestapp.db.dto.AuthorDTO;
import net.aspect.education.thymeleaftestapp.db.dto.mapper.Mapper;
import net.aspect.education.thymeleaftestapp.db.dto.mapper.MapperAuthor;
import net.aspect.education.thymeleaftestapp.db.entity.Author;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final MapperAuthor mapper = new MapperAuthor();

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Получение всех DTO авторов списком*/
    @Override
    @Transactional
    public List<AuthorDTO> getAll() {
        List<Author> all = authorRepository.findAll();
        return all
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public Optional<AuthorDTO> getById(int id) {
        Optional<Author> resultAuthor = authorRepository.findById(id);
        // TODO: реализовать ПРАВИЛЬНУЮ обработку ошибок
        if (resultAuthor.isEmpty()) throw new NullPointerException("Автора с таким ID нет");

        Author resultAuthorObject = resultAuthor.get();
        return Optional.of(mapper.toDTO(resultAuthorObject));
    }

    @Override
    @Transactional
    public Optional<AuthorDTO> getByName(String name) {
        List<Author> authorByName = authorRepository.getAuthorByName(name);

        // TODO: реализовать ПРАВИЛЬНУЮ обработку ошибок
        if (authorByName.isEmpty()) throw new NullPointerException("Автора(ов) с таким именем нет");

        Author resultAuthor = authorByName.getFirst();
        return Optional.of(mapper.toDTO(resultAuthor));
    }

    @Override
    @Transactional
    public AuthorDTO addAuthor(AuthorDTO author) {
        Author currentAuthor = mapper.toEntity(author);

        Set<String> books = author.getBookList();

        for(String s : books){
            Book findBook = bookRepository.getBookByName(s);
            if (findBook != null) currentAuthor.addBookToAuthor(findBook);

            Book ifNotFindBook = new Book();
            ifNotFindBook.setName(s);

            currentAuthor.addBookToAuthor(ifNotFindBook);
        }

        Author savedAuthor = authorRepository.save(currentAuthor);

        return mapper.toDTO(savedAuthor);
    }

    @Override
    @Transactional
    public void deleteAuthor(int id) {
        Optional<Author> byId = authorRepository.findById(id);

        if(byId.isEmpty()) throw new NullPointerException("Автора с Таким ID не существует");

        Author currentAuthor = byId.get();
        currentAuthor.getBooks().stream().forEach(book -> book.removeAuthor(currentAuthor));
        authorRepository.delete(currentAuthor);
    }

    /**
     * Данный метод может обновить только имя!
     * Нет добавления книг */
    @Override
    @Transactional
    public AuthorDTO updateAuthor(AuthorDTO authorDTO) {
        Optional<Author> byId = authorRepository.findById(authorDTO.getId());

        if (byId.isEmpty()) throw new NullPointerException("Автора с таким ID нет. Обновления не будет");

        Author author = mapper.toEntity(authorDTO);
        author.setBooks(byId.get().getBooks());

        authorRepository.save(author);
        return mapper.toDTO(author);
    }
}
