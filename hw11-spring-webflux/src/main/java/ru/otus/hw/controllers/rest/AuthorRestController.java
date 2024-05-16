package ru.otus.hw.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.repositories.AuthorRepository;

@RestController
@RequiredArgsConstructor
public class AuthorRestController {

    private final AuthorRepository authorRepository;


    @GetMapping("/api/author")
    public Flux<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().map(AuthorDto::from);
    }

}
