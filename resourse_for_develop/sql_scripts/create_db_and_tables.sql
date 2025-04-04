-- сначала создаём БД
CREATE DATABASE bookshelf;
-- создаём таблицы после создания БД bookshelf
CREATE TABLE  books (
    id SERIAL,
    name_book VARCHAR(50) NOT NULL UNIQUE,
    year INTEGER,
    link_file_description VARCHAR(100),

    PRIMARY KEY (id)
);

CREATE TABLE authors (
    id SERIAL,
    name_author VARCHAR(30) NOT NULL UNIQUE,

    PRIMARY KEY (id)
);

CREATE TABLE books_authors (
    book_id INT NOT NULL,
    author_id INT NOT NULL,

    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (author_id) REFERENCES authors(id)
);