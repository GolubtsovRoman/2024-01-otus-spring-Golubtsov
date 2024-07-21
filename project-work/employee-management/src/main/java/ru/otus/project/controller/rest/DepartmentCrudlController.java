package ru.otus.project.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.project.controller.dto.DepartmentUpdateDto;
import ru.otus.project.dto.DepartmentDto;
import ru.otus.project.service.DepartmentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DepartmentCrudlController {

    private final DepartmentService departmentService;


    @PostMapping("/department")
    public DepartmentDto create(@RequestBody DepartmentDto departmentDto) {
        return departmentService.create(
                departmentDto.code(),
                departmentDto.name(),
                departmentDto.description()
        );
    }

    @GetMapping("/department/{code}")
    public DepartmentDto read(@PathVariable String code) {
        return departmentService.findByCode(code);
    }

    @PutMapping("/department/{code}")
    public DepartmentDto update(@PathVariable String code, @RequestBody DepartmentUpdateDto departmentUpdateDto) {
        return departmentService.update(
                code,
                departmentUpdateDto.name(),
                departmentUpdateDto.description()
        );
    }

    @DeleteMapping("/department/{code}")
    public void delete(@PathVariable String code) {
        departmentService.delete(code);
    }

    @GetMapping("/department")
    public List<DepartmentDto> list() {
        return departmentService.findAll();
    }

}
