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
class BookRepositoryJpaTest {

    private static final int BOOKS_COUNT = 3;


    @Autowired
    private BookRepository bookRepository;


    @DisplayName("возращать список всех книг")
    @Test
    void findAll() {
        var books = bookRepository.findAll();
        assertThat(books).hasSize(BOOKS_COUNT);
        books.stream().forEach(book -> assertThat(book.getAuthor()).isNotNull());
    }

}
