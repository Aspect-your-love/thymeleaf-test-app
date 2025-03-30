package net.aspect.education.thymeleaftestapp.controller;

import net.aspect.education.thymeleaftestapp.db.dto.AuthorDTO;
import net.aspect.education.thymeleaftestapp.db.service.authorservice.AuthorServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
@RestController
@RequestMapping("/api/authors")
public class AuthorAPIController {

    private final AuthorServiceImpl authorService;

    public AuthorAPIController(AuthorServiceImpl authorService){
        this.authorService = authorService;
    }

    @GetMapping("/all")
    public List<AuthorDTO> getAllAuthors(){
        return authorService.getAll();
    }

    @GetMapping("/{id}")
    public AuthorDTO getAuthorByID(@PathVariable("id") int id){
        return authorService.getById(id).get();
    }

    @GetMapping("/{name}")
    public AuthorDTO getAuthorByName(@PathVariable("name") String name){
        return authorService.getByName(name).get();
    }

    @PostMapping
    public AuthorDTO addAuthor(AuthorDTO authorDTO){
        return authorService.addAuthor(authorDTO);
    }

    @PutMapping
    public AuthorDTO updateAuthor(AuthorDTO authorDTO){
        return authorService.updateAuthor(authorDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable("id") int id){
        authorService.deleteAuthor(id);
    }

}
