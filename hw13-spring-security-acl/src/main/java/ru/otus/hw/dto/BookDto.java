package ru.otus.hw.dto;

import ru.otus.hw.models.Book;

import java.util.List;

public record BookDto(
        long id,
        String title,
        AuthorDto authorDto,
        List<GenreDto> genresDto
) {

    public static BookDto from(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                AuthorDto.from(book.getAuthor()),
                book.getGenres().stream().map(GenreDto::from).toList()
        );
    }

}
