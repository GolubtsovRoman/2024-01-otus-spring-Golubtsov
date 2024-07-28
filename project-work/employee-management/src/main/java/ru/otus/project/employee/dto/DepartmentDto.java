package ru.otus.project.employee.dto;

import ru.otus.project.employee.model.Department;

/**
 * DTO for {@link ru.otus.project.model.Department}
 */
public record DepartmentDto(String code, String name, String description, EmployeeDto employeeDto) {

    public static DepartmentDto fromEntity(Department department) {
        if (department == null) {
            return null;
        }

        return new DepartmentDto(
                department.getCode(),
                department.getName(),
                department.getDescription(),
                EmployeeDto.fromEntity(department.getManager()));
    }

}
