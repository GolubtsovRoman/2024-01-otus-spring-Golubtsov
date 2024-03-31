package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Book;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class BookConverter {

    private final AuthorConverter authorConverter;

    private final GenreConverter genreConverter;


    public String bookDtoToString(BookDto bookDto) {
        var genresString = bookDto.getGenresDto().stream()
                .map(genreConverter::genreDtoToString)
                .map("{%s}"::formatted)
                .collect(Collectors.joining(", "));

        return "Id: %s, title: %s, author: {%s}, genres: [%s]".formatted(
                bookDto.getId(),
                bookDto.getTitle(),
                authorConverter.authorDtoToString(bookDto.getAuthorDto()),
                genresString);
    }

    public BookDto toDto(Book book) {
        var authorDto = authorConverter.toDto(book.getAuthor());
        var genresDto = book.getGenres().stream().map(genreConverter::toDto).toList();
        return new BookDto(
                book.getId(),
                book.getTitle(),
                authorDto,
                genresDto
        );
    }

}
