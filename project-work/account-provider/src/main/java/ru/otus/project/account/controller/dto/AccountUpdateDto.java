package ru.otus.project.account.controller.dto;

import ru.otus.project.account.dto.AccountDto;

import java.time.LocalDate;

public record AccountUpdateDto(String login, String password, LocalDate expirePassword, String email, Boolean active) {

    public AccountDto toAccountDto() {
        return new AccountDto(null, login, password, expirePassword, email, active);
    }

    public AccountDto toAccountDto(String id) {
        return new AccountDto(id, login, password, expirePassword, email, active);
    }

}
