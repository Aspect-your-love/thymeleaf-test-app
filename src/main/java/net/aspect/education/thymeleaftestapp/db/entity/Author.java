package net.aspect.education.thymeleaftestapp.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "authors")
@Getter
@Setter
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name_author")
    private String name;

    // TODO: Проблема - JSON_IGNORE. Создать контроллер, а затем получить автора и его книги. Через DTO.
    /*@ManyToMany(cascade = {CascadeType.DETACH
            , CascadeType.MERGE
            , CascadeType.PERSIST
            , CascadeType.REFRESH})*/
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="books_authors"
            , joinColumns = @JoinColumn(name="author_id")
            , inverseJoinColumns = @JoinColumn(name="book_id")
    )
    private List<Book> books;

    public Author (String name) {
        this.name = name;
    }

    public void addBookToAuthor(Book book){
        if (books == null) books = new ArrayList<>();

        books.add(book);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Author author)) return false;
        return Objects.equals(name, author.name) && Objects.equals(books, author.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, books);
    }
}
