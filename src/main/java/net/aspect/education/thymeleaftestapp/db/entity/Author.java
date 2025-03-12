package net.aspect.education.thymeleaftestapp.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name_author")
    private String name;

    // TODO: Проблема - JSON_IGNORE. Создать контроллер, а затем получить автора и его книги.
    @ManyToMany(cascade = {CascadeType.DETACH
            , CascadeType.MERGE
            , CascadeType.PERSIST
            , CascadeType.REFRESH})
    @JoinTable(name="books_authors"
            , joinColumns = @JoinColumn(name="author_id")
            , inverseJoinColumns = @JoinColumn(name="book_id")
    )
    @JsonIgnore
    private List<Book> books;

    public Author (String name) {
        this.name = name;
    }

    public void addBookToAuthor(Book book){
        if (books == null) books = new ArrayList<>();

        books.add(book);
    }
}
