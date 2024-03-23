package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Comment должен")
@DataJpaTest
@Import(CommentRepositoryJpa.class)
class CommentRepositoryJpaTest {

    private static final int FIRST_COMMENT_ID = 1;
    private static final int NEW_COMMENT_ID = 7;
    private static final int COMMENT_FOR_UPDATE_ID = 6;
    private static final String NEW_COMMENT_NOTE = "newNote";

    private static final int FIRST_BOOK_ID = 1;
    private static final int SECOND_BOOK_ID = 2;
    private static final int COUNT_OF_COMMENT_FOR_BOOK = 2;


    @Autowired
    private CommentRepositoryJpa commentRepositoryJpa;

    @Autowired
    private TestEntityManager em;


    @DisplayName("возвращать комментарий по id")
    @Test
    void findById() {
        var expectedComment = em.find(Comment.class, FIRST_COMMENT_ID);
        var optionalComment = commentRepositoryJpa.findById(FIRST_COMMENT_ID);
        assertThat(optionalComment).isNotEmpty().get()
                .usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("возвращать все комментарии по id книги")
    @Test
    void findAllCommentsByBookId() {
        var book = em.find(Book.class, SECOND_BOOK_ID);
        var comments = commentRepositoryJpa.findAllCommentsByBookId(SECOND_BOOK_ID);
        assertThat(comments).hasSize(COUNT_OF_COMMENT_FOR_BOOK);
        comments.forEach(comment -> {
            assertThat(comment).isNotNull();
            assertThat(comment.getBook()).usingRecursiveComparison().isEqualTo(book);
        });
    }

    @DisplayName("сохранять комментарий")
    @Test
    void insert() {
        var notExistComment = em.find(Comment.class, NEW_COMMENT_ID);
        assertThat(notExistComment).isNull();

        var bookForNewComment = em.find(Book.class, SECOND_BOOK_ID);
        var newComment = new Comment(
                0,
                NEW_COMMENT_NOTE,
                bookForNewComment
        );

        var savedComment = commentRepositoryJpa.save(newComment);
        newComment.setId(NEW_COMMENT_ID);
        assertThat(savedComment).isNotNull()
                        .usingRecursiveComparison().isEqualTo(newComment);

        em.flush();

        var comment7 = em.find(Comment.class, NEW_COMMENT_ID);
        assertThat(comment7).isNotNull()
                .usingRecursiveComparison().isEqualTo(newComment);

        em.remove(comment7);
        em.flush();
    }

    @DisplayName("обновлять комментарий")
    @Test
    void update() {
        var commentForUpdate = em.find(Comment.class, COMMENT_FOR_UPDATE_ID);
        assertThat(commentForUpdate).isNotNull();
        var bookFromCommentForUpdate = commentForUpdate.getBook();
        assertThat(bookFromCommentForUpdate).isNotNull();

        var newBookForComment = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(newBookForComment).isNotNull()
                        .usingRecursiveComparison().isNotEqualTo(bookFromCommentForUpdate);
        em.clear();

        var updatingComment = new Comment(
                COMMENT_FOR_UPDATE_ID,
                NEW_COMMENT_NOTE,
                newBookForComment
        );
        assertThat(updatingComment).isNotEqualTo(commentForUpdate);

        commentRepositoryJpa.save(updatingComment);
        em.flush();

        var optinalUpdatedComment = em.find(Comment.class, COMMENT_FOR_UPDATE_ID);
        assertThat(optinalUpdatedComment).isNotNull()
                .usingRecursiveComparison().isEqualTo(updatingComment);
        assertThat(optinalUpdatedComment).isNotNull()
                .usingRecursiveComparison().isNotEqualTo(commentForUpdate);
    }

    @DisplayName("удалять кооментарий по id")
    @Test
    void deleteById() {
        var comment = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(comment).isNotNull();

        commentRepositoryJpa.deleteById(FIRST_COMMENT_ID);
        em.flush();

        var deletedComment = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(deletedComment).isNull();
    }

}
