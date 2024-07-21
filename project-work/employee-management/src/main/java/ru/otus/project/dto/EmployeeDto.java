package ru.otus.project.dto;

/**
 * DTO for {@link ru.otus.project.model.Employee}
 */
public record EmployeeDto(long id, PersonalInfoDto personalInfo, WorkInfoDto workInfo, String accountId) { }
