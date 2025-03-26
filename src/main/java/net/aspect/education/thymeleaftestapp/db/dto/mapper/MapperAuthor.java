package net.aspect.education.thymeleaftestapp.db.dto.mapper;

import net.aspect.education.thymeleaftestapp.db.dto.AuthorDTO;
import net.aspect.education.thymeleaftestapp.db.entity.Author;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MapperAuthor implements Mapper<Author, AuthorDTO> {

    @Override
    public AuthorDTO toDTO(Author entity) {
        AuthorDTO dto = new AuthorDTO();
        dto.setName(entity.getName());
        dto.setBookList(entity
                .getBooks()
                .stream()
                .map(Book::getName)
                .collect(Collectors.toSet()));
        return dto;
    }

    /**
     * Преобразует DTO в Author. Однако, объект Author
     * не содержит в себе книг
     */
    @Override
    public Author toEntity(AuthorDTO dto) {
        return new Author(dto.getName());
    }
}
