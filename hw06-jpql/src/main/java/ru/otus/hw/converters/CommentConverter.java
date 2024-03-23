package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Comment;

@RequiredArgsConstructor
@Component
public class CommentConverter {

    private final BookConverter bookConverter;


    public String commentDtoToString(CommentDto commentDto) {
        return "Id: %d, note: %s, book: {%s}".formatted(
                commentDto.getId(),
                commentDto.getNote(),
                bookConverter.bookDtoToString(commentDto.getBookDto())
        );
    }

    public CommentDto toDto(Comment comment) {
        var bookDto = bookConverter.toDto(comment.getBook());
        return new CommentDto(
                comment.getId(),
                comment.getNote(),
                bookDto
        );
    }

}
