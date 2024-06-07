package ru.otus.hw.processors;

import jakarta.validation.constraints.NotNull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.jpa.JpaAuthor;
import ru.otus.hw.models.mongo.MongoAuthor;

@Service
public class AuthorItemProcessor implements ItemProcessor<JpaAuthor, MongoAuthor> {

    @Override
    public MongoAuthor process(@NotNull JpaAuthor item) {
        return new MongoAuthor(
                String.valueOf(item.getId()),
                item.getFullName()
        );
    }

}
