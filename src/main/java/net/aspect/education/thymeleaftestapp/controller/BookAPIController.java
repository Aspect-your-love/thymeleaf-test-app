package net.aspect.education.thymeleaftestapp.controller;

import net.aspect.education.thymeleaftestapp.db.dto.BookDTO;
import net.aspect.education.thymeleaftestapp.db.service.bookservice.BookService;
import net.aspect.education.thymeleaftestapp.db.service.bookservice.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookAPIController {

    private final BookServiceImpl bookService;

    @Autowired
    public BookAPIController(BookServiceImpl bookService){
        this.bookService = bookService;
    }

    @GetMapping("/allBooks")
    public List<BookDTO> showAllBooks(){
        return bookService.getAll();
    }

    @GetMapping("/getBook/{id}")
    public BookDTO showBookById(@PathVariable("id") int id){
        return bookService.getById(id);
    }

    @PostMapping("/addBook")
    public void addBook(@RequestBody BookDTO book){
        bookService.addBook(book);
    }

    /// Данный метод удаляет книгу по заданному ID
    @DeleteMapping("/deleteBook/{id}")
    public void deleteBook(@PathVariable int id){
        bookService.deleteBook(id);
    }

    @PutMapping("/updateBook")
    public BookDTO updateBook(@RequestBody BookDTO bookDTO){
        return bookService.updateBook(bookDTO);
    }
}
