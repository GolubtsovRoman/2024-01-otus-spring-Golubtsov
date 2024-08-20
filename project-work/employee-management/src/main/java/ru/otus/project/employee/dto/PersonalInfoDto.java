package ru.otus.project.employee.dto;

import ru.otus.project.employee.model.PersonalInfo;

import java.time.LocalDate;

/**
 * DTO for {@link ru.otus.project.employee.model.PersonalInfo}
 */
public record PersonalInfoDto(long id, String fullName, LocalDate birthdate, LocalDate employmentDate, boolean isMan) {

    public static PersonalInfoDto fromEntity(PersonalInfo personalInfo) {
        if (personalInfo == null) {
            return null;
        }

        return new PersonalInfoDto(
                personalInfo.getId(),
                personalInfo.getFullName(),
                personalInfo.getBirthdate(),
                personalInfo.getEmploymentDate(),
                personalInfo.isMan()
        );
    }

}
