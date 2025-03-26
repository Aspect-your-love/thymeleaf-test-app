-- Проверить то, "чистые ли" таблицы в БД
INSERT INTO authors
    (id, name_author)
VALUES
    (1, 'Аниэла Яффе'),
    (2, 'Карл Густав Юнг'),
    (3, 'Аркадий Стругацкий'),
    (4, 'Борис Стругацкий');

INSERT INTO books
    (id, name_book, publication_year, link_file_description)
VALUES
    (1, 'Воспоминания. Сновидения. Размышления', 1961, 'static/book-descriptions/18 - Воспоминания. Сновидения. Размышления - Аниэла Яффе, Карл Густав Юнг.txt'),
    (2, 'Сознание и бессознательное', 1997, 'static/book-descriptions/23 - Сознание и бессознательное - Карл Густав Юнг.txt'),
    (3, 'Град обречённый', 1987, 'static/book-descriptions/29 - Град обречённый - Аркадий Стругацкий, Борис Стругацкий.txt');

INSERT INTO books_authors
    (book_id, author_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 2),
    (3, 3),
    (3, 4);

/*
    Поиграться с различными JOIN-ами.
    1 - CROSS JOIN
    2 - LEFT JOIN
    3 - RIGHT JOIN
    4 - SELF JOIN
 */