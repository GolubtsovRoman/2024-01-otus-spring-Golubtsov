package ru.otus.project.loader.dto;

import java.time.LocalDate;

public record AccountUpdateDto(String login, String password, LocalDate expirePassword, String email, Boolean active) {
}
