package net.aspect.education.thymeleaftestapp.db.service.bookservice;

import net.aspect.education.thymeleaftestapp.db.dao.book.BookRepository;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getAllBook() {
        List<Book> allBook = bookRepository.findAll();
        System.out.println("Были найдены книги: "+ allBook);
        return allBook;
    }

    @Override
    public Book getBookById(int id) {
        Optional<Book> getBookOptional = bookRepository.findById(id);

        if(getBookOptional.isEmpty())
            throw new NullPointerException(String.format("Object with %d not exist.", id));

        return getBookOptional.get();
    }

    @Override
    public void saveOrUpdateBook(Book newBook) {
        bookRepository.save(newBook);
    }

    @Override
    public void deleteBookById(int id) {
        bookRepository.deleteById(id);
    }
}
