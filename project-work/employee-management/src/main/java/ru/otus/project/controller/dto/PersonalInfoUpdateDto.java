package ru.otus.project.controller.dto;

import java.time.LocalDate;

public record PersonalInfoUpdateDto(String fullName, LocalDate birthdate, LocalDate employmentDate, boolean isMan) {
}
