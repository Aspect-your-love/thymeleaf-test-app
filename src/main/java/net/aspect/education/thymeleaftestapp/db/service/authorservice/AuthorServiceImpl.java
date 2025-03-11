package net.aspect.education.thymeleaftestapp.db.service.authorservice;

import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import net.aspect.education.thymeleaftestapp.db.dao.author.AuthorRepository;
import net.aspect.education.thymeleaftestapp.db.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService{

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    @Transactional
    public List<Author> getAllAuthor() {
        return authorRepository.findAll();
    }

    @Override
    @Transactional
    public Author getAuthorById(int id) {
        Optional<Author> authorCheck = authorRepository.findById(id);

        if (authorCheck.isEmpty())
            throw new NullPointerException(String.format("Author with %d not exist", id));

        return authorCheck.get();
    }

    @Override
    @Transactional
    public void saveOrUpdateAuthor(Author author) {
        authorRepository.save(author);
    }

    @Override
    @Transactional
    public void deleteAuthorById(int id) {
        authorRepository.deleteById(id);
    }
}
