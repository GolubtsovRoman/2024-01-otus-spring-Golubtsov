package ru.otus.hw.services;

import ru.otus.hw.dto.BookDto;

import java.util.List;
import java.util.Set;

public interface BookService {

    BookDto findById(long id);

    List<BookDto> findAll();

    BookDto insert(String title, String authorFullName, Set<String> genresNames);

    BookDto update(long id, String title, String authorFullName, Set<String> genresNames);

    void deleteById(long id);

}
