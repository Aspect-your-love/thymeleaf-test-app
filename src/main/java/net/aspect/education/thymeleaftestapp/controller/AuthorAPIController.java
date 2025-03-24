package net.aspect.education.thymeleaftestapp.controller;

import net.aspect.education.thymeleaftestapp.db.dto.AuthorDTO;
import net.aspect.education.thymeleaftestapp.db.dto.Mapper;
import net.aspect.education.thymeleaftestapp.db.service.authorservice.AuthorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorAPIController {

    private final AuthorServiceImpl authorServiceImpl;

    private final Mapper mapper;

    @Autowired
    public AuthorAPIController(AuthorServiceImpl authorServiceImpl, Mapper mapper) {
        this.authorServiceImpl = authorServiceImpl;
        this.mapper = mapper;
    }

    public List<AuthorDTO> getAllAuthors(){
        /*TODO:// написать сервис, который будет преобразовывать всех авторов в DTO.*/
        return null;
    }
}
