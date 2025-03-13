package net.aspect.education.thymeleaftestapp.db.dto;

import net.aspect.education.thymeleaftestapp.db.entity.Author;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MapperTest {
    private Book book1 ;
    private List<Author> authors;
    private final Mapper mapper = new Mapper();

    @BeforeEach
    public void beforeEach() {
        authors = new ArrayList<>();
        authors.add(new Author("Шёлохов"));

        book1 = new Book("Тихий дон", 1888, "file_path.txt", authors);
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

        assertThat(mapper.toBook(bookDTO)).isEqualTo(book1);
    }

    @Test
    public void testMappingToBookDTO(){
        // TODO: реализовать тест
    }
}
