package ru.otus.project.employee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.project.employee.dto.OfficeDto;
import ru.otus.project.employee.exception.EntityNotFoundException;
import ru.otus.project.employee.model.Office;
import ru.otus.project.employee.repository.OfficeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfficeService {

    private final OfficeRepository officeRepository;


    @Transactional
    public OfficeDto create(String address, int capacity, String description) {
        Office office = new Office(0, address, capacity, description);
        Office savedOffice = officeRepository.save(office);
        return OfficeDto.fromEntity(savedOffice);
    }

    @Transactional(readOnly = true)
    public OfficeDto findById(long id) {
        return officeRepository.findById(id)
                .map(OfficeDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Office with id=%d not found".formatted(id)));
    }

    @Transactional
    public OfficeDto update(long id, String address, int capacity, String description) {
        Office office = officeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Office with id=%d not found".formatted(id)));
        office.setAddress(address);
        office.setCapacity(capacity);
        office.setDescription(description);

        Office updatedOffice = officeRepository.save(office);
        return OfficeDto.fromEntity(updatedOffice);
    }

    @Transactional
    public void delete(long id) {
        officeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<OfficeDto> findAll() {
        return officeRepository.findAll()
                .stream().map(OfficeDto::fromEntity)
                .toList();
    }

}
