package ru.otus.hw.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import static org.springframework.util.CollectionUtils.isEmpty;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    private final GenreRepository genreRepository;


    @PostMapping("/api/book")
    public Mono<BookDto> createBook(@RequestBody BookDto bookDto) {
        return save(bookDto).map(BookDto::from);
    }

    @GetMapping("/api/book")
    public Flux<BookDto> readAllBooks() {
        return bookRepository.findAll().map(BookDto::from);
    }

    @GetMapping("/api/book/{id}")
    public Mono<BookDto> readBook(@PathVariable String id) {
        return bookRepository.findById(id).map(BookDto::from);
    }

    @PutMapping("/api/book/{id}")
    public Mono<BookDto> updateBook(@RequestBody BookDto bookDto) {
        return save(bookDto).map(BookDto::from);
    }


    @DeleteMapping("/api/book/{id}")
    public void deleteBook(@PathVariable String id) {
        bookRepository.deleteById(id).block();
    }


    private Mono<Book> save(BookDto bookDto) {
        var genresNames = bookDto.genresNames();
        if (isEmpty(genresNames)) {
            throw new IllegalArgumentException("Genres ids must not be null");
        }

        var author = authorRepository.findByFullName(bookDto.authorName()).blockOptional()
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Author with fullName %s not found".formatted(bookDto.authorName())
                        ));
        var genres = genresNames.stream()
                .map((genreName) -> {
                    return genreRepository.findByName(genreName).blockOptional()
                            .orElseThrow(() ->
                                    new EntityNotFoundException("Genre with name %s not found".formatted(genreName)));
                })
                .toList();
        if (isEmpty(genres) || bookDto.genresNames().size() != genres.size()) {
            throw new EntityNotFoundException("One or all genres with names %s not found".formatted(genresNames));
        }

        var book = new Book(bookDto.id(), bookDto.title(), author, genres);
        return bookRepository.save(book);
    }

}
