package net.aspect.education.thymeleaftestapp.db.dto;

import net.aspect.education.thymeleaftestapp.db.dto.mapper.Mapper;
import net.aspect.education.thymeleaftestapp.db.dto.mapper.MapperAuthor;
import net.aspect.education.thymeleaftestapp.db.entity.Author;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

// TODO: протестировать маппер для Author
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={
        net.aspect.education.thymeleaftestapp.ThymeleafTestAppApplication.class})
public class AuthorMapperTest {

    private MapperAuthor mapper;

    private Author author;
    private AuthorDTO authorDto;

    @Autowired
    public AuthorMapperTest(MapperAuthor mapper){
        this.mapper = mapper;
    }

    @BeforeEach
    public void createObject(){
        String authorName = new String("Duma");

        author = new Author(authorName);
        author.setBooks(new ArrayList<>());
        authorDto = new AuthorDTO();
        authorDto.setName(authorName);
    }

    @Test
    public void toAuthor(){
        Author newAuthor = mapper.toEntity(authorDto);
        //Добавляем книги текущему автору ИЗ РЕПОЗИТОРИЯ, если таковые есть
        newAuthor.setBooks(new ArrayList<>());

        assertAll(
                () -> assertThat(newAuthor.getName()).isEqualTo(authorDto.getName()),
                () -> assertThat(newAuthor.getBooks()).isEmpty()
        );
    }

    @Test
    public void toAuthorDTO() {
//        TODO: реализовать
        AuthorDTO newAuthorDTO = mapper.toDTO(author);

        assertAll(
                () -> assertThat(newAuthorDTO.getName()).isEqualTo(authorDto.getName()),
                () -> assertThat(newAuthorDTO.getBookList()).isEmpty()
        );
    }
}
