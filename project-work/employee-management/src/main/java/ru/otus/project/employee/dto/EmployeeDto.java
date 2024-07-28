package ru.otus.project.employee.dto;

import ru.otus.project.employee.model.Employee;

/**
 * DTO for {@link ru.otus.project.model.Employee}
 */
public record EmployeeDto(long id,
                          PersonalInfoDto personalInfo,
                          String jobTitle,
                          Long managerEmployeeId,
                          DepartmentDto department,
                          OfficeDto office,
                          Integer additionalNumber,
                          String accountId) {

    public static EmployeeDto fromEntity(Employee employee) {
        if (employee == null) {
            return null;
        }

        Long managerId = null;
        Employee manager = employee.getManagerEmployee();
        if (manager != null) {
            managerId = manager.getId();
        }

        return new EmployeeDto(
                employee.getId(),
                PersonalInfoDto.fromEntity(employee.getPersonalInfo()),
                employee.getJobTitle(),
                managerId,
                DepartmentDto.fromEntity(employee.getDepartment()),
                OfficeDto.fromEntity(employee.getOffice()),
                employee.getAdditionalNumber(),
                employee.getAccountId()
        );
    }

}
