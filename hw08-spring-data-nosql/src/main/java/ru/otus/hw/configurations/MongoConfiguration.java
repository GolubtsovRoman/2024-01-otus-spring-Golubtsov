package ru.otus.hw.configurations;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongock
@EnableMongoRepositories
public class MongoConfiguration {}
