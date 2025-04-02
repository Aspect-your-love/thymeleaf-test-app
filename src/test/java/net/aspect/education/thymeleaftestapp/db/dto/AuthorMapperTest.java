package net.aspect.education.thymeleaftestapp.db.dto;

import net.aspect.education.thymeleaftestapp.db.dto.mapper.MapperAuthor;
import net.aspect.education.thymeleaftestapp.db.entity.Author;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

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
        author.setBooks(new HashSet<>());
        authorDto = new AuthorDTO();
        authorDto.setName(authorName);
    }

    @Test
    @DisplayName("Из DTO в Author")
    public void toAuthor(){
        Author newAuthor = mapper.toEntity(authorDto);
        //Добавляем книги текущему автору ИЗ РЕПОЗИТОРИЯ, если таковые есть
        newAuthor.setBooks(new HashSet<>());

        assertAll(
                () -> assertThat(newAuthor.getName()).isEqualTo(authorDto.getName()),
                () -> assertThat(newAuthor.getBooks()).isEmpty()
        );
    }

    @Test
    @DisplayName("Из Author в DTO")
    public void toAuthorDTO() {
        AuthorDTO newAuthorDTO = mapper.toDTO(author);

        assertAll(
                () -> assertThat(newAuthorDTO.getName()).isEqualTo(authorDto.getName()),
                () -> assertThat(newAuthorDTO.getBookList()).isEmpty()
        );
    }
}
