package net.aspect.education.thymeleaftestapp.db.service.authorservice;

import net.aspect.education.thymeleaftestapp.db.dto.AuthorDTO;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<AuthorDTO> getAll();

    Optional<AuthorDTO> getById(int id);

    Optional<AuthorDTO> getByName(String name);

    AuthorDTO addAuthor(AuthorDTO author);

    void deleteAuthor(int id);

    AuthorDTO updateAuthor(AuthorDTO authorDTO);
}
