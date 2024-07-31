package ru.otus.project.employee.dto;

import ru.otus.project.employee.model.Department;

/**
 * DTO for {@link ru.otus.project.employee.model.Department}
 */
public record DepartmentDto(String code, String name, String description, long managerId) {

    public static DepartmentDto fromEntity(Department department) {
        if (department == null) {
            return null;
        }

        return new DepartmentDto(
                department.getCode(),
                department.getName(),
                department.getDescription(),
                department.getManagerId());
    }

}
