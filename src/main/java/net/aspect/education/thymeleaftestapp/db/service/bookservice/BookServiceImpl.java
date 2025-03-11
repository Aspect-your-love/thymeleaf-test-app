package net.aspect.education.thymeleaftestapp.db.service.bookservice;

import jakarta.transaction.Transactional;
import net.aspect.education.thymeleaftestapp.db.dao.author.AuthorRepository;
import net.aspect.education.thymeleaftestapp.db.dao.book.BookRepository;
import net.aspect.education.thymeleaftestapp.db.entity.Author;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    /**
     * Получение всех книг из БД
     */
    @Override
    @Transactional
    public List<Book> getAllBook() {
        List<Book> allBook = bookRepository.findAll();
        System.out.println("Были найдены книги: " + allBook);
        return allBook;
    }

    /**
     * Получение книги по ID
     */
    @Override
    @Transactional
    public Book getBookById(int id) {
        Optional<Book> getBookOptional = bookRepository.findById(id);

        if (getBookOptional.isEmpty())
            throw new NullPointerException(String.format("Object with %d not exist.", id));

        return getBookOptional.get();
    }

    // TODO: проверить работу данного метода
    /**
     * Сохранение или обновление книги
     */
    @Override
    @Transactional
    public Book saveOrUpdateBook(Book newBook) {
        List<Author> authorThisBook = newBook.getAuthors()
                .stream()
                .filter(author -> bookRepository.findAll().stream().toList().contains(author))
                .toList();

        for(Author a : authorThisBook){
            newBook.addAuthorToBook(a);
        }
       return bookRepository.save(newBook);
    }

    /**
     * Удаление книги из БД
     */
    @Override
    @Transactional
    public void deleteBookById(int id) {
        bookRepository.deleteById(id);
    }


}


/*@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public User createUser(User user) {
        // Обрабатываем роли: сохраняем только новые
        Set<Role> roles = user.getRoles().stream()
            .map(role -> roleRepository.findByName(role.getName())
                .orElseGet(() -> roleRepository.save(role))) // Если роли нет, сохраняем
            .collect(Collectors.toSet());

        user.setRoles(roles); // Устанавливаем роли

        return userRepository.save(user); // Сохраняем пользователя
    }
}*/