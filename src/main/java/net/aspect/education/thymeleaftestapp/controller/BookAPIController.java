package net.aspect.education.thymeleaftestapp.controller;

import net.aspect.education.thymeleaftestapp.db.dto.BookDTO;
import net.aspect.education.thymeleaftestapp.db.dto.Mapper;
import net.aspect.education.thymeleaftestapp.db.service.bookservice.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookAPIController {


    private final BookService bookService;

    private Mapper mapper = new Mapper();

    @Autowired
    public BookAPIController(BookService bookService){
        this.bookService = bookService;
    }

    //9 TODO: Исправить данный метод. Сделать так, чтобы возвращался список DTO.
    @GetMapping("/allBooks")
    public List<BookDTO> showAllBooks(){
        return bookService.getAllBook();
    }

    @GetMapping("/getBook/{id}")
    public BookDTO showBookById(@PathVariable("id") int id){
        return bookService.getBookById(id);
    }

    @PostMapping("/addBook")
    public void addBook(@RequestBody BookDTO book){
        bookService.saveBook(book);
    }

    /// Данный метод удаляет книгу по заданному ID
    @DeleteMapping("/deleteBook/{id}")
    public void deleteBook(@PathVariable int id){
        bookService.deleteBookById(id);
    }
}
