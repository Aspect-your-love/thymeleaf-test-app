package net.aspect.education.thymeleaftestapp.db.dto;

import net.aspect.education.thymeleaftestapp.db.dto.mapper.MapperBookWithoutAuthor;
import net.aspect.education.thymeleaftestapp.db.entity.Author;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MapperBookWithoutAuthorTest {
    private Book book1 ;
    private List<Author> authors;
    private final MapperBookWithoutAuthor mapperBookWithoutAuthor = new MapperBookWithoutAuthor();
    private BookDTO bookDTO;

    @BeforeEach
    public void beforeEach() {
        authors = new ArrayList<>();
        authors.add(new Author("Шёлохов"));

        book1 = new Book("Тихий дон", 1888, "file_path.txt", authors);
        bookDTO = new BookDTO();
        bookDTO.setAuthorsName(book1.getAuthors().stream().map(Author::getName).toList());
        bookDTO.setName(book1.getName());
        bookDTO.setYear(book1.getYear());
        bookDTO.setFilePath(book1.getFilePath());
    }

    @AfterEach
    public void afterEach() {
        authors.clear();
        book1 = null;
    }


    @Test
    public void testMappingToBook(){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setName(book1.getName());
        bookDTO.setYear(book1.getYear());
        bookDTO.setFilePath(book1.getFilePath());
        bookDTO.setAuthorsName(book1
                .getAuthors()
                .stream()
                .map(Author::getName)
                .collect(Collectors.toList()));
        book1.setAuthors(new ArrayList<>());

        assertThat(mapperBookWithoutAuthor.toEntity(bookDTO)).isEqualTo(book1);
    }

    @Test
    public void testMappingToBookDTO(){
        BookDTO bookDTOTest = mapperBookWithoutAuthor.toDTO(book1);
        assertThat(bookDTOTest).isEqualTo(bookDTO);
    }

}
