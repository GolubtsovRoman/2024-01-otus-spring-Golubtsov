package ru.otus.project.dto;

/**
 * DTO for {@link ru.otus.project.model.WorkInfo}
 */
public record WorkInfoDto(long id,
                          String jobTitle,
                          // todo employee
                          DepartmentDto department,
                          OfficeDto office,
                          int additionalNumber) { }
