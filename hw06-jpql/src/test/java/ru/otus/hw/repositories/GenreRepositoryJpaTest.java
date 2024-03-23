package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Genre;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Genre должен")
@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {

    private static final int GENRES_COUNT = 6;
    private static final Set<Long> SET_GENRE_IDS = Set.of(1L, 2L, 3L);

    @Autowired
    private GenreRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;


    @DisplayName("возвращать список всех жанров")
    @Test
    void findAll() {
        var genres = repositoryJpa.findAll();
        assertThat(genres).hasSize(GENRES_COUNT);
    }

    @DisplayName("возвразать список жанров по списку id")
    @Test
    void findAllByIds() {
        var genres = repositoryJpa.findAllByIds(SET_GENRE_IDS);
        assertThat(genres).hasSize(SET_GENRE_IDS.size());
        SET_GENRE_IDS.forEach(id -> {
            var genre = em.find(Genre.class, id);
            assertThat(genres).contains(genre);
        });
    }

}
