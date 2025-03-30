package net.aspect.education.thymeleaftestapp.db.dto;

import net.aspect.education.thymeleaftestapp.db.dto.mapper.MapperBookWithoutAuthor;
import net.aspect.education.thymeleaftestapp.db.entity.Author;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("mapping")
public class MapperBookWithoutAuthorTest {
    private Book book1 ;
    private Set<Author> authors;
    private final MapperBookWithoutAuthor mapperBookWithoutAuthor = new MapperBookWithoutAuthor();
    private BookDTO bookDTO;

    @BeforeEach
    public void beforeEach() {
        authors = new HashSet<>();
        authors.add(new Author("Шёлохов"));

        book1 = new Book("Тихий дон", 1888, "file_path.txt", authors);
        bookDTO = new BookDTO();
        bookDTO.setAuthorsName(book1.getAuthors().stream().map(Author::getName).collect(Collectors.toSet()));
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
    @DisplayName("Из DTO в book")
    public void testMappingToBook(){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setName(book1.getName());
        bookDTO.setYear(book1.getYear());
        bookDTO.setFilePath(book1.getFilePath());
        bookDTO.setAuthorsName(book1
                .getAuthors()
                .stream()
                .map(Author::getName)
                .collect(Collectors.toSet()));
        book1.setAuthors(new HashSet<>());

        assertThat(mapperBookWithoutAuthor.toEntity(bookDTO)).isEqualTo(book1);
    }


    @Test
    @DisplayName("Из Book в DTO")
    public void testMappingToBookDTO(){
        BookDTO bookDTOTest = mapperBookWithoutAuthor.toDTO(book1);
        assertThat(bookDTOTest).isEqualTo(bookDTO);
    }

}
