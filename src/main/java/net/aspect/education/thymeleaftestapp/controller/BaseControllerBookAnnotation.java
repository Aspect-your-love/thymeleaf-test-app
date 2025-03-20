package net.aspect.education.thymeleaftestapp.controller;

import jakarta.annotation.PostConstruct;
import net.aspect.education.thymeleaftestapp.db.entity.Author;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import net.aspect.education.thymeleaftestapp.db.service.authorservice.AuthorService;
import net.aspect.education.thymeleaftestapp.db.service.bookservice.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BaseControllerBookAnnotation {


    private final BookService bookService;

    @Autowired
    public BaseControllerBookAnnotation(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<Book> showAllBooks(){
        return bookService.getAllBook();
    }

    @GetMapping("/book/{id}")
    public Book showBookById(@PathVariable("id") int id){
        return bookService.getBookById(id);
    }

    //TODO: добавить метод, который будет выводить книги по указанию автора

    /*TODO: Метод SaveOrUpdate работает неправильно
    1) Данный метод должен добавляться сразу и книгу, и автора. Без автора нельзя добавлять книгу.
    Для добавления использовать метод из entity Book.
    2) Также, проводить проверку на то, является ли поле Author пустым.*/

    /*
    @PostMapping("/book")
    public void addBook(@RequestBody Book book){
        if (book.getAuthors().isEmpty()) throw new NullPointerException(String.format("В книге %s нет авторов", book.getName()));
        bookService.saveOrUpdateBook(book);
    }*/

    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable int id){

        Book book = bookService.getBookById(id);

        if (book == null) throw new NullPointerException("Нет такой книги.");

        bookService.deleteBookById(id);
    }
}
