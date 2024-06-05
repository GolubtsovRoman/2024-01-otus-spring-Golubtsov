package ru.otus.hw.dto;

import ru.otus.hw.models.Genre;

public record GenreDto (
    long id,
    String name
) {

    public static GenreDto from(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }

}
