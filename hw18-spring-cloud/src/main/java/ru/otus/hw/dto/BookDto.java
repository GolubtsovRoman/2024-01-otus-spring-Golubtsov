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

    public static BookDto stub() {
        return new BookDto(0, "N/A", AuthorDto.stub(), GenreDto.listStub());
    }

    public static List<BookDto> listStub() {
        return List.of(stub());
    }

}
