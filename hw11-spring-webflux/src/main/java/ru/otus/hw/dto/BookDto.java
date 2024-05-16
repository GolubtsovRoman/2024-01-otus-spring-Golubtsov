package ru.otus.hw.dto;

import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.Set;
import java.util.stream.Collectors;

public record BookDto(
        String id,
        String title,
        String authorName,
        Set<String> genresNames
) {

    public static BookDto from(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor().getFullName(),
                book.getGenres().stream().map(Genre::getName).collect(Collectors.toSet())
        );
    }

}
