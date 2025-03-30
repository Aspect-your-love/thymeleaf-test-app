package net.aspect.education.thymeleaftestapp.db.service.authorservice;

import jakarta.transaction.Transactional;
import net.aspect.education.thymeleaftestapp.db.dto.AuthorDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    /**
     * Получение всех авторов
     * через DTO списком*/
    @Override
    @Transactional
    public List<AuthorDTO> getAll() {
        return List.of();
    }

    @Override
    @Transactional
    public Optional<AuthorDTO> getById(int id) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<AuthorDTO> getByName(String name) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public AuthorDTO addAuthor(AuthorDTO author) {
        return null;
    }

    @Override
    @Transactional
    public void deleteAuthor(int id) {

    }

    @Override
    @Transactional
    public AuthorDTO updateAuthor(AuthorDTO authorDTO) {
        return null;
    }
}
