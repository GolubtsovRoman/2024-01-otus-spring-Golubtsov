package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;
import java.util.Set;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;


    @Transactional(readOnly = true)
    @Override
    public BookDto findById(long id) {
        return bookRepository.findById(id)
                .map(BookDto::from)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(id)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(BookDto::from)
                .toList();
    }

    @Transactional
    @Override
    public BookDto insert(String title, String authorFullName, Set<String> genresNames) {
        var book = save(0, title, authorFullName, genresNames);
        return BookDto.from(book);
    }

    @Transactional
    @Override
    public BookDto update(long id, String title, String authorFullName, Set<String> genresNames) {
        var book = save(id, title, authorFullName, genresNames);
        return BookDto.from(book);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }


    private Book save(long id, String title, String authorFullName, Set<String> genresNames) {
        if (isEmpty(genresNames)) {
            throw new IllegalArgumentException("Genres ids must not be null");
        }

        var author = authorRepository.findByFullName(authorFullName)
                .orElseThrow(() ->
                        new EntityNotFoundException("Author with fullName %s not found".formatted(authorFullName)));
        var genres = genresNames.stream()
                .map((genreName) -> {
                    return genreRepository.findByName(genreName)
                            .orElseThrow(() ->
                                    new EntityNotFoundException("Genre with name %s not found".formatted(genreName)));
                })
                .toList();
        if (isEmpty(genres) || genresNames.size() != genres.size()) {
            throw new EntityNotFoundException("One or all genres with names %s not found".formatted(genresNames));
        }

        var book = new Book(id, title, author, genres);
        return bookRepository.save(book);
    }

}

