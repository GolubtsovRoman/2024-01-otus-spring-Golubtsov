package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.hw.models.Book;

import java.util.List;

@Data
@AllArgsConstructor
public class BookDto {

    private long id;

    private String title;

    private AuthorDto authorDto;

    private List<GenreDto> genresDto;


    public static BookDto from(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                AuthorDto.from(book.getAuthor()),
                book.getGenres().stream().map(GenreDto::from).toList()
        );
    }

}
