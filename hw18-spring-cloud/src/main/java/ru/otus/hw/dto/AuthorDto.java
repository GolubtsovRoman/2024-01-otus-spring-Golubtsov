package ru.otus.hw.dto;

import ru.otus.hw.models.Author;

public record AuthorDto(
        long id,
        String fullName
) {

    public static AuthorDto from(Author author) {
        return new AuthorDto(author.getId(), author.getFullName());
    }

    public static AuthorDto stub() {
        return new AuthorDto(0, "N/A");
    }

}
