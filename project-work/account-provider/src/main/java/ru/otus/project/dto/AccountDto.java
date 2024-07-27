package ru.otus.project.dto;

import ru.otus.project.model.Account;

import java.time.LocalDate;

/**
 * DTO for {@link ru.otus.project.model.Account}
 */
public record AccountDto(String id,
                         String login,
                         String password,
                         LocalDate expirePassword,
                         String email,
                         Boolean active) {

    public static AccountDto fromEntity(Account account) {
        return new AccountDto(
                account.getId(),
                account.getLogin(),
                account.getPassword(),
                account.getExpirePassword(),
                account.getEmail(),
                account.getActive()
        );
    }

}
