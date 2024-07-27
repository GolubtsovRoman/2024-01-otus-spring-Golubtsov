package ru.otus.project.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.project.model.Account;

import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account, String> {

    Optional<Account> findByLogin(String login);

    Optional<Account> findByEmail(String email);

}
