package net.aspect.education.thymeleaftestapp.db.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Table(name="books")
@Entity
@Setter
@Getter
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Column(name="name_book")
    private String name;

    @Column(name="year")
    private int year;

    @Column(name="link_file_description")
    private String file_path;

    @ManyToMany(cascade = {CascadeType.DETACH
            , CascadeType.MERGE
            , CascadeType.PERSIST
            , CascadeType.REFRESH})
    @JoinTable(name="books_authors"
            , joinColumns = @JoinColumn(name="book_id")
            , inverseJoinColumns = @JoinColumn(name="author_id")
    )
    @JsonManagedReference
    private List<Author> authors;

    public Book(){

    }

    public Book(String name, int year, String file_path) {
        this.name = name;
        this.year = year;
        this.file_path = file_path;
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
}
