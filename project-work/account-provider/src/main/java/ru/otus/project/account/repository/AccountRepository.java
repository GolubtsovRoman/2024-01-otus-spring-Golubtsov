package ru.otus.project.account.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.project.account.model.Account;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account, String> {

    Optional<Account> findByLogin(String login);

    Optional<Account> findByEmail(String email);

    List<Account> findByExpirePassword(LocalDate expirePassword);

}
