package net.aspect.education.thymeleaftestapp.db.service.bookservice;

import jakarta.transaction.Transactional;
import net.aspect.education.thymeleaftestapp.db.dao.book.BookRepository;
import net.aspect.education.thymeleaftestapp.db.dto.BookDTO;
import net.aspect.education.thymeleaftestapp.db.dto.Mapper;
import net.aspect.education.thymeleaftestapp.db.entity.Author;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    private Mapper mapper = new Mapper();

    /**
     * Получение всех книг из БД
     */
    @Override
    @Transactional
    public List<BookDTO> getAllBook() {
        List<Book> allBook = bookRepository.findAll();
        System.out.println("Были найдены книги: " + allBook);

        List<BookDTO> bookDTOList = allBook.stream().map(book -> mapper.toBookDTO(book)).toList();

        return bookDTOList;
    }

    /**
     * Получение книги по ID
     */
    @Override
    @Transactional
    public Book getBookById(int id) {
        Optional<Book> getBookOptional = bookRepository.findById(id);

        if (getBookOptional.isEmpty())
            throw new NullPointerException(String.format("Object with %d not exist.", id));

        return getBookOptional.get();
    }

    // TODO: проверить работу данного метода
    /**
     * Сохранение или обновление книги
     */
    @Override
    @Transactional
    public Book saveOrUpdateBook(Book newBook) {
       return bookRepository.save(newBook);
    }

    /**
     * Удаление книги из БД
     */
    @Override
    @Transactional
    public void deleteBookById(int id) {
        bookRepository.deleteById(id);
    }


}