package ru.otus.project.employee.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.project.employee.dto.EmployeeDto;
import ru.otus.project.employee.service.DepartmentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;


    @GetMapping("/department/{code}/employee")
    public List<EmployeeDto> getAllDepartmentEmployee(@PathVariable String code) {
        return departmentService.findAllEmployeeInDepartment(code.toUpperCase());
    }

    @GetMapping("/department/{code}/employee/manager")
    public EmployeeDto getManager(@PathVariable String code) {
        return departmentService.findDepartmentManager(code.toUpperCase());
    }

}
