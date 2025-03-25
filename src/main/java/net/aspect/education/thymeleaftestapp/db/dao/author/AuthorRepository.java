package net.aspect.education.thymeleaftestapp.db.dao.author;

import net.aspect.education.thymeleaftestapp.db.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    List<Author> getAuthorByName(String name);
}
