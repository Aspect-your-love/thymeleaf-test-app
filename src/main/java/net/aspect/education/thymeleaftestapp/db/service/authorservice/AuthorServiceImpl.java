package net.aspect.education.thymeleaftestapp.db.service.authorservice;

import net.aspect.education.thymeleaftestapp.db.dto.AuthorDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    /**
     * Получение всех авторов
     * через DTO списком*/
    @Override
    public List<AuthorDTO> getAll() {
        return List.of();
    }
}
