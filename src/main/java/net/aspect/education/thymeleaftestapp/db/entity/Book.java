package net.aspect.education.thymeleaftestapp.db.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name="books")
@Entity
@Setter
@Getter
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name_book")
    private String name;

    @Column(name="year")
    private int year;

    @Column(name="link_file_description")
    private String filePath;

    @ManyToMany(cascade = {CascadeType.DETACH
            , CascadeType.MERGE
            , CascadeType.PERSIST
            , CascadeType.REFRESH})
    @JoinTable(name="books_authors"
            , joinColumns = @JoinColumn(name="book_id")
            , inverseJoinColumns = @JoinColumn(name="author_id")
    )
    private List<Author> authors;

    public Book(){
    }

    public Book(String name, int year, String filePath, List<Author> authors) {
        this.name = name;
        this.year = year;
        this.filePath = filePath;
        this.authors = authors;
    }

    public void addAuthorToBook(Author author){
        if (authors == null) authors = new ArrayList<>();

        authors.add(author);
    }

    /*public List<AuthorDTO> getAuthorNames() {
        return authors.stream()
                .map(author -> new AuthorDTO(author.getName()))
                .collect(Collectors.toList());
    }*/

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Book book)) return false;
        return year == book.year && Objects.equals(name, book.name) && Objects.equals(filePath, book.filePath) && Objects.equals(authors, book.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year, filePath, authors);
    }
}
