package ru.otus.project.controller.dto;

public record EmployeeUpdatedDto(
        long id,
        long personalInfoId,
        String jobTitle,
        Long managerEmployeeId,
        String departmentCode,
        Long officeId,
        Integer additionalNumber,
        String accountId) {
}
