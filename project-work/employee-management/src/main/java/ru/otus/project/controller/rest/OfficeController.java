package ru.otus.project.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.project.dto.EmployeeDto;
import ru.otus.project.service.EmployeeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OfficeController {

    private final EmployeeService employeeService;


    @GetMapping("/office/{id}/employee")
    public List<EmployeeDto> getAllEmployeesInOffice(@PathVariable long id) {
        return employeeService.findAllInOffice(id);
    }

    @GetMapping("/office/remote")
    public List<EmployeeDto> getAllEmployeesInRemote() {
        return employeeService.findAllRemote();
    }

}
