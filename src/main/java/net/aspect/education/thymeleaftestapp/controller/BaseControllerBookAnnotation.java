package net.aspect.education.thymeleaftestapp.controller;

import jakarta.annotation.PostConstruct;
import net.aspect.education.thymeleaftestapp.db.dto.BookDTO;
import net.aspect.education.thymeleaftestapp.db.dto.Mapper;
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

    private Mapper mapper = new Mapper();

    @Autowired
    public BaseControllerBookAnnotation(BookService bookService){
        this.bookService = bookService;
    }

    //9 TODO: Исправить данный метод. Сделать так, чтобы возвращался список DTO.
    @GetMapping("/books")
    public List<BookDTO> showAllBooks(){

        //!!! Возвращать DTO
        return bookService.getAllBook();
    }

    @GetMapping("/book/{id}")
    public BookDTO showBookById(@PathVariable("id") int id){
        return bookService.getBookById(id);
    }

    //TODO: добавить метод, который будет выводить книги по указанию автора

    /*TODO: Метод SaveOrUpdate работает неправильно
    1) Данный метод должен добавляться сразу и книгу, и автора. Без автора нельзя добавлять книгу.
    Для добавления использовать метод из entity Book.
    2) Также, проводить проверку на то, является ли поле Author пустым.*/


    @PostMapping("/book")
    public void addBook(@RequestBody BookDTO book){
        bookService.saveBook(book);
    }

    /// Данный метод удаляет книгу по заданному ID
    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable int id){
        bookService.deleteBookById(id);
    }
}
