package ru.otus.hw.converters;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.models.Author;

@Component
public class AuthorConverter {

    public String authorDtoToString(AuthorDto authorDto) {
        return "Id: %d, FullName: %s".formatted(authorDto.getId(), authorDto.getFullName());
    }

    public AuthorDto toDto(Author author) {
        return new AuthorDto(
                author.getId(),
                author.getFullName()
        );
    }

}
