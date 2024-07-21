package ru.otus.project.dto;

import ru.otus.project.model.Office;

/**
 * DTO for {@link ru.otus.project.model.Office}
 */
public record OfficeDto(long id, String address, int capacity, String description) {

    public static OfficeDto fromEntity(Office office) {
        return new OfficeDto(office.getId(), office.getAddress(), office.getCapacity(), office.getDescription());
    }

}
