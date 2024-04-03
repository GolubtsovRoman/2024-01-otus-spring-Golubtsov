package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Comment должен")
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.hw.configurations", "ru.otus.hw.repositories"})
class MongoCommentRepositoryTest {

    private static final int COUNT_OF_COMMENT_FOR_BOOK = 2;


    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;


    @DisplayName("возвращать все комментарии по id книги")
    @Test
    void findByBookId() {
        Book firstBook = bookRepository.findAll().stream().findFirst().get();
        String firstBookId = firstBook.getId();

        List<Comment> commentsByBookId = commentRepository.findByBookId(firstBookId);
        assertThat(commentsByBookId).hasSize(COUNT_OF_COMMENT_FOR_BOOK);
        commentsByBookId.forEach(comment -> {
            assertThat(comment).isNotNull();
            assertThat(comment.getBook()).usingRecursiveComparison().isEqualTo(firstBook);
        });
    }

}
