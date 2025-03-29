package net.aspect.education.thymeleaftestapp.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Table(name="books")
@Entity
@Setter
@Getter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name_book", unique = true, nullable = false)
    private String name;

    @Column(name="publication_year")
    private int year;

    @Column(name="link_file_description")
    private String filePath;

    @ManyToMany(mappedBy = "books"
            , cascade = {CascadeType.DETACH
            , CascadeType.MERGE
            , CascadeType.PERSIST
            , CascadeType.REFRESH})
    /*@JoinTable(name="books_authors"
            , joinColumns = @JoinColumn(name="book_id", referencedColumnName="id")
            , inverseJoinColumns = @JoinColumn(name="author_id", referencedColumnName="id")
    )*/
    private Set<Author> authors;

    public Book(){
    }

    public Book(String name, int year, String filePath, Set<Author> authors) {
        this.name = name;
        this.year = year;
        this.filePath = filePath;
        this.authors = authors;
    }

    public void addAuthorToBook(Author author){
        if (authors == null) authors = new HashSet<>();

        authors.add(author);
        if (author.getBooks() == null) author.setBooks(new HashSet<>());
        author.getBooks().add(this);
    }

    public void removeAuthor(Author author){
        authors.remove(author);
    }
    /**
     * PreRemove метод.<br>
     * Вызывается перед тем, как удалить сущность, <br>
     * а следовательно удаляет все связи во вспомогательной таблице<br> */
    @PreRemove
    public void removeAuthorAssociations(){
        for (Author a : this.authors){
            a.getBooks().remove(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Book book)) return false;
        return year == book.year && Objects.equals(name, book.name) && Objects.equals(filePath, book.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year, filePath);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", filePath='" + filePath + '\'' +
                ", authors: " + authors.stream().map(Author::getName).toList() +
                '}';
    }
}
