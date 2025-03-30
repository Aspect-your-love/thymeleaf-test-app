package net.aspect.education.thymeleaftestapp.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

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

    @Column(name = "name_author", nullable = false, unique = true)
    private String name;

    @ManyToMany(cascade = {CascadeType.DETACH
            , CascadeType.MERGE
            , CascadeType.PERSIST
            , CascadeType.REFRESH}
            , fetch = FetchType.EAGER)
    @JoinTable(name="books_authors"
            , joinColumns = @JoinColumn(name="author_id", referencedColumnName="id")
            , inverseJoinColumns = @JoinColumn(name="book_id", referencedColumnName="id")
    )
    private Set<Book> books;

    public Author(String name) {
        this.name = name;
    }

    public void addBookToAuthor(Book book) {
        if (books == null) books = new HashSet<>();

        books.add(book);
        if (book.getAuthors() == null) book.setAuthors(new HashSet<>());
        book.getAuthors().add(this);
    }

    public void removeBookAssociations(Book removeBook){
        this.books.remove(removeBook);
        removeBook.getAuthors().remove(this);
    }

    public void removeBookList(){
        books.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Author author)) return false;
        return Objects.equals(name, author.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", books= " + books.stream().map(Book::getName).toList() +
                '}';
    }
}
