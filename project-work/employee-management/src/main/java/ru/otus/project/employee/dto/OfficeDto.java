package ru.otus.project.employee.dto;

import ru.otus.project.employee.model.Office;

/**
 * DTO for {@link ru.otus.project.model.Office}
 */
public record OfficeDto(long id, String address, int capacity, String description) {

    public static OfficeDto fromEntity(Office office) {
        if (office == null) {
            return null;
        }
        return new OfficeDto(office.getId(), office.getAddress(), office.getCapacity(), office.getDescription());
    }

}
