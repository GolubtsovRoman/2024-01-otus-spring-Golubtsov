package ru.otus.project.employee.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.project.employee.dto.EmployeeDto;
import ru.otus.project.employee.service.EmployeeService;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;


    @GetMapping("/employee/{id}/manager")
    public EmployeeDto getManager(@PathVariable long id) {
        return employeeService.findManagerById(id);
    }

}
