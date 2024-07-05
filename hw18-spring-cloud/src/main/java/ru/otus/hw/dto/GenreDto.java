package ru.otus.hw.dto;

import ru.otus.hw.models.Genre;

import java.util.List;

public record GenreDto (
    long id,
    String name
) {

    public static GenreDto from(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }

    public static List<GenreDto> listStub() {
        return List.of(new GenreDto(0, "N/A"));
    }

}
