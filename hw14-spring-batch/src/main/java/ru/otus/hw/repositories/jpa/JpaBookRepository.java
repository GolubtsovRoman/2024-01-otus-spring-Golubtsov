package ru.otus.hw.repositories.jpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.otus.hw.models.jpa.JpaBook;

import java.util.List;
import java.util.Optional;

public interface JpaBookRepository extends PagingAndSortingRepository<JpaBook, Long> {

    @EntityGraph("book-graph")
    List<JpaBook> findAll();

    @EntityGraph("book-graph")
    Optional<JpaBook> findById(Long id);

}
