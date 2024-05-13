package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    private final CommentConverter commentConverter;


    @Transactional(readOnly = true)
    @Override
    public Optional<CommentDto> findById(long id) {
        return commentRepository.findById(id)
                .map(commentConverter::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> findAllCommentsByBookId(long bookId) {
        return commentRepository.findByBookId(bookId).stream()
                .map(commentConverter::toDto)
                .toList();
    }

    @Transactional
    @Override
    public CommentDto insert(String note, long bookId) {
        var comment = save(0, note, bookId);
        return commentConverter.toDto(comment);
    }

    @Transactional
    @Override
    public CommentDto update(long id, String note, long bookId) {
        var comment = save(id, note, bookId);
        return commentConverter.toDto(comment);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }


    private Comment save(long id, String note, long bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));

        var comment = new Comment(id, note, book);
        return commentRepository.save(comment);
    }

}
