package ru.otus.hw.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@ChangeLog
public class DatabaseChangelog {

    private static final String ROMAN_GOLUBTSOV = "Roman Golubtsov"; //changelog author

    private final List<Author> authors = new ArrayList<>(3);

    private final List<Genre> genres = new ArrayList<>(6);

    private final List<Book> books = new ArrayList<>(3);


    @ChangeSet(order = "001", id = "dropDb", author = ROMAN_GOLUBTSOV, runAlways = true)
    public void dropDb(MongoDatabase mdb) {
        mdb.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = ROMAN_GOLUBTSOV, runAlways = true)
    public void insertAuthors(AuthorRepository authorRepository) {
        authors.add(new Author(null, "Author_1"));
        authors.add(new Author(null, "Author_2"));
        authors.add(new Author(null, "Author_3"));
        authorRepository.insert(authors);
    }

    @ChangeSet(order = "003", id = "insertGenres", author = ROMAN_GOLUBTSOV, runAlways = true)
    public void insertGenres(GenreRepository genreRepository) {
        genres.add(new Genre(null, "Genre_1"));
        genres.add(new Genre(null, "Genre_2"));
        genres.add(new Genre(null, "Genre_3"));
        genres.add(new Genre(null, "Genre_4"));
        genres.add(new Genre(null, "Genre_5"));
        genres.add(new Genre(null, "Genre_6"));
        genreRepository.insert(genres);
    }

    @ChangeSet(order = "004", id = "insertBooks", author = ROMAN_GOLUBTSOV, runAlways = true)
    public void insertBooks(BookRepository bookRepository) {
        books.add(new Book(null, "BookTitle_1", authors.get(0), List.of(genres.get(0), genres.get(1))));
        books.add(new Book(null, "BookTitle_2", authors.get(1), List.of(genres.get(2), genres.get(3))));
        books.add(new Book(null, "BookTitle_3", authors.get(2), List.of(genres.get(4), genres.get(5))));
        bookRepository.insert(books);
    }

    @ChangeSet(order = "005", id = "insertComments", author = ROMAN_GOLUBTSOV, runAlways = true)
    public void insertComments(CommentRepository commentRepository) {
        var comment1 = new Comment(null, "good_comment_1", books.get(0));
        var comment2 = new Comment(null, "bad_comment_1", books.get(0));
        var comment3 = new Comment(null, "good_comment_2", books.get(1));
        var comment4 = new Comment(null, "bad_comment_2", books.get(1));
        var comment5 = new Comment(null, "good_comment_3", books.get(2));
        var comment6 = new Comment(null, "bad_comment_3", books.get(2));
        var comments = List.of(comment1, comment2, comment3, comment4, comment5, comment6);
        commentRepository.insert(comments);
    }

}
