package ru.otus.hw.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.controllers.rest.dto.BookViewDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.services.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    @PostMapping("/api/book")
    public BookViewDto createBook(@RequestBody BookViewDto bookViewDto) {
        BookDto bookDto = bookService.insert(bookViewDto.title(), bookViewDto.authorName(), bookViewDto.genresNames());
        return BookViewDto.from(bookDto);
    }

    @GetMapping("/api/book")
    public List<BookViewDto> readAllBooks() {
        return bookService.findAll().stream().map(BookViewDto::from).toList();
    }

    @GetMapping("/api/book/{id}")
    public BookViewDto readBook(@PathVariable long id) {
        BookDto bookDto = bookService.findById(id);
        return BookViewDto.from(bookDto);
    }

    @PutMapping("/api/book/{id}")
    public BookViewDto updateBook(@RequestBody BookViewDto bookViewDto) {
        BookDto bookDto = bookService.update(
                bookViewDto.id(),
                bookViewDto.title(),
                bookViewDto.authorName(),
                bookViewDto.genresNames()
        );
        return BookViewDto.from(bookDto);
    }


    @DeleteMapping("/api/book/{id}")
    public void deleteBook(@PathVariable long id) {
        bookService.deleteById(id);
    }

}
