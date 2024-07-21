package ru.otus.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.project.dto.DepartmentDto;
import ru.otus.project.exception.EntityAlreadyExistsException;
import ru.otus.project.exception.EntityNotFoundException;
import ru.otus.project.model.Department;
import ru.otus.project.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;


    @Transactional
    public DepartmentDto create(String code, String name, String description) {
        Optional<Department> optionalDepartment = departmentRepository.findById(code);
        if (optionalDepartment.isPresent()) {
            throw new EntityAlreadyExistsException("department with code=%s already exists".formatted(code));
        }

        Department department = new Department(code, name, description);
        Department savedDepartment = departmentRepository.save(department);
        return DepartmentDto.fromEntity(savedDepartment);
    }

    @Transactional(readOnly = true)
    public DepartmentDto findByCode(String code) {
        return departmentRepository.findById(code)
                .map(DepartmentDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Department with code=%s not found".formatted(code)));
    }

    @Transactional
    public DepartmentDto update(String code, String name, String description) {
        Department department = departmentRepository.findById(code)
                .orElseThrow(() -> new EntityNotFoundException("Department with code=%s not found".formatted(code)));
        department.setCode(code);
        department.setName(name);
        department.setDescription(description);

        Department updatedDepartment = departmentRepository.save(department);
        return DepartmentDto.fromEntity(updatedDepartment);
    }

    @Transactional
    public void delete(String code) {
        departmentRepository.deleteById(code);
    }

    @Transactional(readOnly = true)
    public List<DepartmentDto> findAll() {
        return departmentRepository.findAll()
                .stream().map(DepartmentDto::fromEntity)
                .toList();
    }

}
