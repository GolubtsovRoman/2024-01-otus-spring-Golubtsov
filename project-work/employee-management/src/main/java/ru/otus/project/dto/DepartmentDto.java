package ru.otus.project.dto;

import ru.otus.project.model.Department;

/**
 * DTO for {@link ru.otus.project.model.Department}
 */
public record DepartmentDto(String code, String name, String description) {

    public static DepartmentDto fromEntity(Department department) {
        return new DepartmentDto(department.getCode(), department.getName(), department.getDescription());
    }

}
