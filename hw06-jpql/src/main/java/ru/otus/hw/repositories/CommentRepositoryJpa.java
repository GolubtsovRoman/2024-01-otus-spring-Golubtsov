package ru.otus.hw.repositories;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;


    @Override
    public Optional<Comment> findById(long id) {
        var comment = em.find(Comment.class, id);
        return Optional.ofNullable(comment);
    }

    @Override
    public List<Comment> findAllCommentsByBookId(long bookId) {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-entity-graph");
        var query = em.createQuery("select c from Comment c where book.id = :bookId", Comment.class);
        query.setParameter("bookId", bookId);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public void deleteById(long id) {
        var comment = em.find(Comment.class, id);
        if (comment != null) {
            em.remove(comment);
        } else {
            throw new EntityNotFoundException("Comment with id %d not found".formatted(id));
        }
    }

}
