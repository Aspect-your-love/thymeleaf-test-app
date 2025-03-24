package net.aspect.education.thymeleaftestapp.controller;

import net.aspect.education.thymeleaftestapp.db.dto.AuthorDTO;
import net.aspect.education.thymeleaftestapp.db.dto.mapper.MapperBookWithoutAuthor;
import net.aspect.education.thymeleaftestapp.db.service.authorservice.AuthorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorAPIController {

    private final AuthorServiceImpl authorServiceImpl;

    private final MapperBookWithoutAuthor mapperBookWithoutAuthor;

    @Autowired
    public AuthorAPIController(AuthorServiceImpl authorServiceImpl, MapperBookWithoutAuthor mapperBookWithoutAuthor) {
        this.authorServiceImpl = authorServiceImpl;
        this.mapperBookWithoutAuthor = mapperBookWithoutAuthor;
    }

    public List<AuthorDTO> getAllAuthors(){
        /*TODO:// написать сервис, который будет преобразовывать всех авторов в DTO.*/
        return null;
    }
}
