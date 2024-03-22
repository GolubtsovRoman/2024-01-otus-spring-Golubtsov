package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Author доожен")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {

    private static final int AUTHORS_COUNT = 3;
    private static final long FIRST_AUTHOR_ID = 1L;


    @Autowired
    private AuthorRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;


    @DisplayName("возвращать список всех авторов")
    @Test
    void findAll() {
        var authors = repositoryJpa.findAll();
        assertThat(authors).hasSize(AUTHORS_COUNT);
    }

    @DisplayName("возвращать автора по его id")
    @Test
    void findById() {
        var optionalAuthor = repositoryJpa.findById(FIRST_AUTHOR_ID);
        var expectedAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(optionalAuthor).isNotEmpty().get()
                .usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

}
