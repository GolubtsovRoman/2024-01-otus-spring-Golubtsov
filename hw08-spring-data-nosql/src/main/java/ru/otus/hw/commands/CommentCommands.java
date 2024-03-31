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
    public String findCommentById(String id) {
        return commentService.findById(id)
                .map(commentConverter::commentDtoToString)
                .orElse("Comment with id %s not found".formatted(id));
    }

    @ShellMethod(value = "Find all comments by book_id", key = "acbbid")
    public String findAllCommentsByBookId(String bookId) {
        return commentService.findAllCommentsByBookId(bookId).stream()
                .map(commentConverter::commentDtoToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    // cins newComment 66085040a5f574709c34738f
    @ShellMethod(value = "Insert comment", key = "cins")
    public String insertComment(String note, String bookId) {
        var savedComment = commentService.insert(note, bookId);
        return commentConverter.commentDtoToString(savedComment);
    }

    // cupd 6608535aa5f574709c34739b editedComment 66085040a5f574709c34738f
    @ShellMethod(value = "Update comment", key = "cupd")
    public String updateComment(String id, String note, String bookId) {
        var savedComment = commentService.update(id, note, bookId);
        return commentConverter.commentDtoToString(savedComment);
    }

    // cdel 6608535aa5f574709c34739b
    @ShellMethod(value = "Delete comment by id", key = "cdel")
    public void deleteCommet(String id) {
        commentService.deleteById(id);
    }

}
