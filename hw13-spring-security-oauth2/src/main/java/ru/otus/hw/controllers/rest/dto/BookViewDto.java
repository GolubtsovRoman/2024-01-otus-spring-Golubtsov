package ru.otus.hw.controllers.rest.dto;

import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;

import java.util.Set;
import java.util.stream.Collectors;

public record BookViewDto(
        long id,
        String title,
        String authorName,
        Set<String> genresNames
) {

    public static BookViewDto from(BookDto bookDto) {
        return new BookViewDto(
                bookDto.getId(),
                bookDto.getTitle(),
                bookDto.getAuthorDto().fullName(),
                bookDto.getGenresDto().stream().map(GenreDto::name).collect(Collectors.toSet())
        );
    }
    
}
