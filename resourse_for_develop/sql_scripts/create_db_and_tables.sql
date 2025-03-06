CREATE DATABASE bookshelf;

\c bookshelf;

CREATE TABLE  books (
    id INTEGER SERIAL,
    name_book VARCHAR(50) NOT NULL,
    year INTEGER,
    link_file_description VARCHAR(100),

    PRIMARY KEY (id)
);

CREATE TABLE authors (
    id INTEGER SERIAL,
    name_author VARCHAR(30) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE books_authors (
    book_id INT NOT NULL,
    author_id INT NOT NULL,

    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES books(id) ON CASCADE DELETE,
    FOREIGN KEY (book_id) REFERENCES authors(id) ON CASCADE DELETE
)