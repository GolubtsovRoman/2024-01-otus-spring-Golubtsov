package ru.otus.hw.dto;

import ru.otus.hw.models.Comment;

public record CommentDto (
        long id,
        String note,
        BookDto bookDto
) {

    public static CommentDto from(Comment comment) {
        return new CommentDto(comment.getId(), comment.getNote(), BookDto.from(comment.getBook()));
    }

}
