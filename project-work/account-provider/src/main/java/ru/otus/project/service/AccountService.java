package ru.otus.project.service;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.project.dto.AccountDto;
import ru.otus.project.exception.EntityNotFoundException;
import ru.otus.project.model.Account;
import ru.otus.project.repository.AccountRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;


    @Transactional
    public AccountDto create(String login, String password, LocalDate expirePassword, String email, Boolean active) {
        if (accountRepository.findByLogin(login).isPresent()) {
            throw new EntityExistsException("Account with login=%s already exists".formatted(login));
        }

        Account account = new Account(null, login, password, expirePassword, email, active);
        Account savedAccount = accountRepository.save(account);
        return AccountDto.fromEntity(savedAccount);
    }

    @Transactional(readOnly = true)
    public AccountDto findById(String id) {
        return accountRepository.findById(id)
                .map(AccountDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Account with id=%s not found".formatted(id)));
    }

    @Transactional
    public AccountDto update(String id,
                             String login,
                             String password,
                             LocalDate expirePassword,
                             String email,
                             Boolean active) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account with id=%s not found".formatted(id)));

        Optional<Account> byLogin = accountRepository.findByLogin(login);
        if (byLogin.isPresent() && !byLogin.get().getId().equals(account.getId())) {
            throw new EntityExistsException("Account with login=%s already exists".formatted(login));
        }

        Optional<Account> byEmail = accountRepository.findByEmail(email);
        if (byLogin.isPresent() && !byLogin.get().getId().equals(account.getId())) {
            throw new EntityExistsException("Account with email=%s already exists".formatted(email));
        }

        account.setLogin(login);
        account.setPassword(password);
        account.setExpirePassword(expirePassword);
        account.setEmail(email);
        account.setActive(active);

        Account updatedAccount = accountRepository.save(account);
        return AccountDto.fromEntity(updatedAccount);
    }

    @Transactional
    public void delete(String id) {
        accountRepository.deleteById(id);
    }

}
