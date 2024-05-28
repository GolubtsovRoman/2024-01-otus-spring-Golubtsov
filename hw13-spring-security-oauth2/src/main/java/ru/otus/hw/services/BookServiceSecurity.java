package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.BookRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceSecurity {

    private final BookRepository bookRepository;

    @PostFilter("hasRole('EDITOR')")
    public List<Book> findAllInRepository() {
        return bookRepository.findAll();
    }


}
