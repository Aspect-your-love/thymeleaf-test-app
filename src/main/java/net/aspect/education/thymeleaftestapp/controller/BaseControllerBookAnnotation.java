package net.aspect.education.thymeleaftestapp.controller;

import net.aspect.education.thymeleaftestapp.db.entity.Book;
import net.aspect.education.thymeleaftestapp.db.service.bookservice.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BaseControllerBookAnnotation {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public List<Book> showAllBooks(){
        return bookService.getAllBook();
    }
}
