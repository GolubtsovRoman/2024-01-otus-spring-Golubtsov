package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcBookRepository implements BookRepository {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    private final GenreRepository genreRepository;

    @Override
    public Optional<Book> findById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        Optional<Book> optionalBook;
        try {
            Book book = namedParameterJdbcOperations.query("""
                            select b.id, b.title, b.author_id, a.full_name, bg.genre_id, g.name from books b
                            inner join authors a ON b.author_id = a.id
                            inner join books_genres bg on b.id = bg.book_id
                            inner join genres g on bg.genre_id = g.id
                            where b.id = :id;
                            """,
                    params, new BookResultSetExtractor());
            optionalBook = Optional.ofNullable(book);
        } catch (DataAccessException dae) {
            optionalBook = Optional.empty();
        }
        return optionalBook;
    }

    @Override
    public List<Book> findAll() {
        var genres = genreRepository.findAll();
        var relations = getAllGenreRelations();
        var books = getAllBooksWithoutGenres();
        mergeBooksInfo(books, genres, relations);
        return books;
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        int status = namedParameterJdbcOperations.update("delete from books where id = :id", params);
        if (status != 0) {
            Book deletedBook = new Book();
            deletedBook.setId(id);
            removeGenresRelationsFor(deletedBook);
        } else {
            throw new EntityNotFoundException("Book with id %d not found".formatted(id));
        }
    }

    private List<Book> getAllBooksWithoutGenres() {
        return namedParameterJdbcOperations.query(
                "select b.id, b.title, b.author_id, a.full_name from books b, authors a where b.author_id = a.id",
                new BookRowMapper()
        );
    }

    private List<BookGenreRelation> getAllGenreRelations() {
        return namedParameterJdbcOperations.query("select book_id, genre_id from books_genres",
                new BookGenreRelationMapper());
    }

    private void mergeBooksInfo(List<Book> booksWithoutGenres, List<Genre> genres,
                                List<BookGenreRelation> relations) {
        // Добавить книгам (booksWithoutGenres) жанры (genres) в соответствии со связями (relations)
        booksWithoutGenres
                .forEach(book -> {
                    List<Genre> genreForBook = relations.stream()
                            .filter(relation -> relation.bookId == book.getId())
                            .map(relation -> genres.stream()
                                    .filter(genre -> genre.getId() == relation.genreId())
                                    .findFirst()
                                    .get()
                            )
                            .toList();
                    book.setGenres(genreForBook);
                });
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("author_id", book.getAuthor().getId());

        namedParameterJdbcOperations.update("insert into books (title, author_id) values (:title, :author_id)",
                params, keyHolder, new String[]{"id"});

        //noinspection DataFlowIssue
        book.setId(keyHolder.getKeyAs(Long.class));
        batchInsertGenresRelationsFor(book);
        return book;
    }

    private Book update(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", book.getId());
        params.addValue("title", book.getTitle());
        params.addValue("author_id", book.getAuthor().getId());

        int countUpdatedRecord = namedParameterJdbcOperations.update(
                "update books set title = :title, author_id = :author_id where id = :id",
                params
        );
        if (countUpdatedRecord < 1) {
            // Выбросить EntityNotFoundException если не обновлено ни одной записи в БД
            throw new EntityNotFoundException("Now one record was update");
        }

        removeGenresRelationsFor(book);
        batchInsertGenresRelationsFor(book);

        return book;
    }

    private void batchInsertGenresRelationsFor(Book book) {
        MapSqlParameterSource[] paramsList = book.getGenres().stream()
                .map(genre -> {
                    MapSqlParameterSource params = new MapSqlParameterSource();
                    params.addValue("book_id", book.getId());
                    params.addValue("genre_id", genre.getId());
                    return params;
                })
                .toArray(MapSqlParameterSource[]::new);

        namedParameterJdbcOperations.batchUpdate(
                "insert into books_genres (book_id, genre_id) values (:book_id, :genre_id)",
                paramsList
        );
    }

    private void removeGenresRelationsFor(Book book) {
        long bookId = book.getId();
        Map<String, Object> params = Collections.singletonMap("book_id", bookId);
        namedParameterJdbcOperations.update("delete from books_genres where book_id = :book_id", params);
    }


    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String title = rs.getString("title");
            long authorId = rs.getLong("author_id");
            String fullName = rs.getString("full_name");

            Author author = new Author(authorId, fullName);
            return new Book(id, title, author, Collections.emptyList());
        }
    }


    // Использовать для findById
    @SuppressWarnings("ClassCanBeRecord")
    @RequiredArgsConstructor
    private static class BookResultSetExtractor implements ResultSetExtractor<Book> {

        @Override
        public Book extractData(ResultSet rs) throws SQLException, DataAccessException {
            rs.next();
            long id = rs.getLong("id");
            String title = rs.getString("title");

            long authorId = rs.getLong("author_id");
            String fullName = rs.getString("full_name");
            Author author = new Author(authorId, fullName);

            List<Genre> genres = new ArrayList<>();
            do {
                long geneId = rs.getLong("genre_id");
                String genreName = rs.getString("name");
                Genre genre = new Genre(geneId, genreName);
                genres.add(genre);
            } while (rs.next());

            return new Book(id, title, author, genres);
        }

    }

    private record BookGenreRelation(long bookId, long genreId) {
    }

    private static class BookGenreRelationMapper implements RowMapper<BookGenreRelation> {

        @Override
        public BookGenreRelation mapRow(ResultSet rs, int rowNum) throws SQLException {
            long bookId = rs.getLong("book_id");
            long genreId = rs.getLong("genre_id");
            return new BookGenreRelation(bookId, genreId);
        }

    }

}
