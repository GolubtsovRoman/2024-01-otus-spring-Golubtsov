package ru.otus.hw.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.hw.models.jpa.JpaAuthor;
import ru.otus.hw.models.jpa.JpaBook;
import ru.otus.hw.models.jpa.JpaGenre;
import ru.otus.hw.models.mongo.MongoAuthor;
import ru.otus.hw.models.mongo.MongoBook;
import ru.otus.hw.models.mongo.MongoGenre;
import ru.otus.hw.processors.AuthorItemProcessor;
import ru.otus.hw.processors.BookItemProcessor;
import ru.otus.hw.processors.GenreItemProcessor;
import ru.otus.hw.repositories.jpa.JpaAuthorRepository;
import ru.otus.hw.repositories.jpa.JpaBookRepository;
import ru.otus.hw.repositories.jpa.JpaGenreRepository;

import java.util.Collections;
import java.util.Map;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class JobConfig {

    public static final String LIBRARY_MIGRATION_JOB = "libraryMigrationJob";

    private static final int CHUNK_SIZE = 5;

    private final Map<String, Sort.Direction> sortsMap = Collections.singletonMap("id", Sort.Direction.ASC);


    private final MongoTemplate mongoTemplate;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;


    private final JpaAuthorRepository jpaAuthorRepository;

    private final JpaGenreRepository jpaGenreRepository;

    private final JpaBookRepository jpaBookRepository;



    @StepScope
    @Bean
    public RepositoryItemReader<JpaAuthor> authorItemReader() {
        RepositoryItemReader<JpaAuthor> reader = new RepositoryItemReader<>();
        reader.setName("authorReader");

        reader.setSort(sortsMap);
        reader.setRepository(jpaAuthorRepository);
        reader.setMethodName("findAll");
        reader.setPageSize(CHUNK_SIZE);
        return reader;
    }

    @StepScope
    @Bean
    public RepositoryItemReader<JpaGenre> genreItemReader() {
        RepositoryItemReader<JpaGenre> reader = new RepositoryItemReader<>();
        reader.setName("genreReader");

        reader.setSort(sortsMap);
        reader.setRepository(jpaGenreRepository);
        reader.setMethodName("findAll");
        reader.setPageSize(CHUNK_SIZE);
        return reader;
    }

    @StepScope
    @Bean
    public RepositoryItemReader<JpaBook> bookItemReader() {
        RepositoryItemReader<JpaBook> reader = new RepositoryItemReader<>();
        reader.setName("bookReader");

        reader.setSort(sortsMap);
        reader.setRepository(jpaBookRepository);
        reader.setMethodName("findAll");
        reader.setPageSize(CHUNK_SIZE);
        return reader;
    }


    @StepScope
    @Bean
    public MongoItemWriter<MongoAuthor> authorWriter() {
        MongoItemWriter<MongoAuthor> writer = new MongoItemWriter<>();
        writer.setTemplate(mongoTemplate);
        writer.setCollection("authors");
        return writer;
    }

    @StepScope
    @Bean
    public MongoItemWriter<MongoGenre> genreWriter() {
        MongoItemWriter<MongoGenre> writer = new MongoItemWriter<>();
        writer.setTemplate(mongoTemplate);
        writer.setCollection("genres");
        return writer;
    }

    @StepScope
    @Bean
    public MongoItemWriter<MongoBook> bookWriter() {
        MongoItemWriter<MongoBook> writer = new MongoItemWriter<>();
        writer.setTemplate(mongoTemplate);
        writer.setCollection("books");
        return writer;
    }


    @Bean
    public Job libraryMigrationJob(Step transformAuthorStep,
                                     Step transformGenreStep,
                                     Step transformBookStep) {
        return new JobBuilder(LIBRARY_MIGRATION_JOB, jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(transformAuthorStep)
                .next(transformGenreStep)
                .next(transformBookStep)
                .end()
                .build();
    }


    @Bean
    public Step transformAuthorStep(RepositoryItemReader<JpaAuthor> reader,
                                    MongoItemWriter<MongoAuthor> writer,
                                    AuthorItemProcessor processor) {
        return new StepBuilder("transformAuthorStep", jobRepository)
                .<JpaAuthor, MongoAuthor>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step transformGenreStep(RepositoryItemReader<JpaGenre> reader,
                                   MongoItemWriter<MongoGenre> writer,
                                   GenreItemProcessor processor) {
        return new StepBuilder("transformAuthorStep", jobRepository)
                .<JpaGenre, MongoGenre>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step transformBookStep(RepositoryItemReader<JpaBook> reader,
                                  MongoItemWriter<MongoBook> writer,
                                  BookItemProcessor processor) {
        return new StepBuilder("transformAuthorStep", jobRepository)
                .<JpaBook, MongoBook>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

}
