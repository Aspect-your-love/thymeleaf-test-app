package net.aspect.education.thymeleaftestapp.db.dao.author;

import net.aspect.education.thymeleaftestapp.db.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Author getAuthorByName(String name);
}
