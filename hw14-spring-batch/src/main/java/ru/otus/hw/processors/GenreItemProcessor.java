package ru.otus.hw.processors;

import jakarta.validation.constraints.NotNull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.jpa.JpaGenre;
import ru.otus.hw.models.mongo.MongoGenre;

@Service
public class GenreItemProcessor implements ItemProcessor<JpaGenre, MongoGenre> {

    @Override
    public MongoGenre process(@NotNull JpaGenre item) {
        return new MongoGenre(
                String.valueOf(item.getId()),
                item.getName()
        );
    }

}
