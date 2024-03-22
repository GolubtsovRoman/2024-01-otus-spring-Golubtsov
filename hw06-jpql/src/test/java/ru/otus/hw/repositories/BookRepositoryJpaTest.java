package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Book должен")
@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {

    private static final int FIRST_BOOK_ID = 1;
    private static final int BOOK_FOR_UPDATE_ID = 1;
    private static final int NEW_BOOK_ID = 4;
    private static final String NEW_BOOK_TITLE = "newBookTitle";
    private static final int SECOND_AUTHOR_ID = 2;
    private static final int FIRST_GENRE_ID = 1;
    private static final int SIXTH_GENRE_ID = 6;
    private static final int BOOKS_COUNT = 3;


    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @Autowired
    private TestEntityManager em;


    @DisplayName("возвращать книгу по id")
    @Test
    void findById() {
        var expectedBook = em.find(Book.class, FIRST_BOOK_ID);
        var optionalBook = bookRepositoryJpa.findById(FIRST_BOOK_ID);
        assertThat(optionalBook).isNotEmpty().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("возращать список всех книг")
    @Test
    void findAll() {
        var books = bookRepositoryJpa.findAll();
        assertThat(books).hasSize(BOOKS_COUNT);
    }

    @DisplayName("сохранять книгу")
    @Test
    void insert() {
        var notExistBook = em.find(Book.class, NEW_BOOK_ID);
        assertThat(notExistBook).isNull();

        var authorForBook = em.find(Author.class, SECOND_AUTHOR_ID);
        var genreForBook = em.find(Genre.class, FIRST_GENRE_ID);
        var newBook = new Book(
                0,
                NEW_BOOK_TITLE,
                authorForBook,
                Collections.singletonList(genreForBook)
        );

        var savedBook = bookRepositoryJpa.save(newBook);
        newBook.setId(NEW_BOOK_ID);
        assertThat(savedBook).isNotNull()
                .usingRecursiveComparison().isEqualTo(newBook);

        em.flush();

        var book4 = em.find(Book.class, NEW_BOOK_ID);
        assertThat(book4).isNotNull()
                .usingRecursiveComparison().isEqualTo(newBook);

        em.remove(book4);
        em.flush();
    }

    @DisplayName("обновлять книгу")
    @Test
    void update() {
        var bookForUpdate = em.find(Book.class, BOOK_FOR_UPDATE_ID);
        assertThat(bookForUpdate).isNotNull();
        var authorFromBookForUpdate = bookForUpdate.getAuthor();
        assertThat(authorFromBookForUpdate).isNotNull();
        var genresFromBookForUpdate = bookForUpdate.getGenres();
        assertThat(genresFromBookForUpdate).isNotEmpty();

        var newAuthorForBook = em.find(Author.class, SECOND_AUTHOR_ID);
        assertThat(newAuthorForBook).isNotNull()
                .usingRecursiveComparison().isNotEqualTo(authorFromBookForUpdate);
        var newGenresForBook = Collections.singletonList(em.find(Genre.class, SIXTH_GENRE_ID));
        assertThat(newGenresForBook).doesNotContainAnyElementsOf(genresFromBookForUpdate);
        em.clear();

        var updatingBook = new Book(
                BOOK_FOR_UPDATE_ID,
                NEW_BOOK_TITLE,
                newAuthorForBook,
                newGenresForBook
        );
        assertThat(updatingBook).isNotEqualTo(bookForUpdate);

        bookRepositoryJpa.save(updatingBook);
        em.flush();

        var optionalUpdatedBook = em.find(Book.class, BOOK_FOR_UPDATE_ID);
        assertThat(optionalUpdatedBook).isNotNull()
                .usingRecursiveComparison().isEqualTo(updatingBook);
        assertThat(optionalUpdatedBook).isNotNull()
                .usingRecursiveComparison().isNotEqualTo(bookForUpdate);
    }

    @DisplayName("удалять книгу по id")
    @Test
    void deleteById() {
        var book = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(book).isNotNull();
        var bookGenres = book.getGenres().stream()
                .map(genre -> em.find(Genre.class, genre.getId()))
                .toList();
        assertThat(book.getGenres()).containsAll(bookGenres);

        bookRepositoryJpa.deleteById(FIRST_BOOK_ID);
        em.flush();

        var deletedBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(deletedBook).isNull();
    }

}
