package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class BookDto {

    private long id;

    private String title;

    private AuthorDto authorDto;

    private List<GenreDto> genresDto;


    public String genersToString() {
        return genresDto.stream().map(GenreDto::getName).collect(Collectors.joining(", "));
    }

}
