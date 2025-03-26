package net.aspect.education.thymeleaftestapp.db.dao.book;

import net.aspect.education.thymeleaftestapp.db.entity.Author;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Book getBookByName(String name);
}
