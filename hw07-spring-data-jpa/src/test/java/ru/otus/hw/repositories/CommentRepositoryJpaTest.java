package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Comment должен")
@DataJpaTest
class CommentRepositoryJpaTest {

    private static final long SECOND_BOOK_ID = 2;
    private static final int COUNT_OF_COMMENT_FOR_BOOK = 2;


    @Autowired
    private CommentRepository commentRepositoryJpa;

    @Autowired
    private BookRepository bookRepository;


    @DisplayName("возвращать все комментарии по id книги")
    @Test
    void findByBookId() {
        var optionalBook = bookRepository.findById(SECOND_BOOK_ID);
        assertThat(optionalBook).isPresent();
        var book = optionalBook.get();

        var comments = commentRepositoryJpa.findByBookId(SECOND_BOOK_ID);
        assertThat(comments).hasSize(COUNT_OF_COMMENT_FOR_BOOK);
        comments.forEach(comment -> {
            assertThat(comment).isNotNull();
            assertThat(comment.getBook()).usingRecursiveComparison().isEqualTo(book);
        });
    }

}
