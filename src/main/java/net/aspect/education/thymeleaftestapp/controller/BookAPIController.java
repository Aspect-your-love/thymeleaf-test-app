package net.aspect.education.thymeleaftestapp.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
public class BookAPIController {


    /*private final BookService bookService;


    @Autowired
    public BookAPIController(BookService bookService){
        this.bookService = bookService;
    }

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
    }*/
}
