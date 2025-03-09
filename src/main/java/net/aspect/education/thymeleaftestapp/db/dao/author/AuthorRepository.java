package net.aspect.education.thymeleaftestapp.db.dao.author;

import net.aspect.education.thymeleaftestapp.db.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

//TODO: добавить реализацию Service для данного репозитория
public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
