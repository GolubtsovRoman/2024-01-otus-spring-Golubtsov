package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.services.CommentService;

import java.util.stream.Collectors;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {

    private final CommentService commentService;

    private final CommentConverter commentConverter;


    @ShellMethod(value = "Find comment by id", key = "cbid")
    public String findCommentById(long id) {
        return commentService.findById(id)
                .map(commentConverter::commentDtoToString)
                .orElse("Comment with id %d not found".formatted(id));
    }

    @ShellMethod(value = "Find all comments by book_id", key = "acbbid")
    public String findAllCommentsByBookId(long bookId) {
        return commentService.findAllCommentsByBookId(bookId).stream()
                .map(commentConverter::commentDtoToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    // cins newComment 1
    @ShellMethod(value = "Insert comment", key = "cins")
    public String insertComment(String note, long bookId) {
        var savedComment = commentService.insert(note, bookId);
        return commentConverter.commentDtoToString(savedComment);
    }

    // cupd 4 editedComment 3
    @ShellMethod(value = "Update comment", key = "cupd")
    public String updateComment(long id, String note, long bookId) {
        var savedComment = commentService.update(id, note, bookId);
        return commentConverter.commentDtoToString(savedComment);
    }

    // cdel 4
    @ShellMethod(value = "Delete comment by id", key = "cdel")
    public void deleteCommet(long id) {
        commentService.deleteById(id);
    }

}
