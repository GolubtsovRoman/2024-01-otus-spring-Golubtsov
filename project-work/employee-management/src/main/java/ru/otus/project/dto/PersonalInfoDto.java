package ru.otus.project.dto;

import java.time.LocalDate;

/**
 * DTO for {@link ru.otus.project.model.PersonalInfo}
 */
public record PersonalInfoDto(long id, String fullName, LocalDate bday, LocalDate employmentDate, boolean man) { }
