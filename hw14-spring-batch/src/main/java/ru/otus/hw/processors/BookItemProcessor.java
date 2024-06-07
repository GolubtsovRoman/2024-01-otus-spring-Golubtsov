package ru.otus.hw.processors;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.jpa.JpaBook;
import ru.otus.hw.models.mongo.MongoAuthor;
import ru.otus.hw.models.mongo.MongoBook;
import ru.otus.hw.models.mongo.MongoGenre;

@Service
@RequiredArgsConstructor
public class BookItemProcessor implements ItemProcessor<JpaBook, MongoBook> {

    private final AuthorItemProcessor authorProcessor;

    private final GenreItemProcessor genreProcessor;


    @Override
    public MongoBook process(@NotNull JpaBook item) {
        MongoAuthor author = authorProcessor.process(item.getAuthor());
        MongoGenre genre = genreProcessor.process(item.getGenre());

        return new MongoBook(
                String.valueOf(item.getId()),
                item.getTitle(),
                author,
                genre
        );
    }

}
