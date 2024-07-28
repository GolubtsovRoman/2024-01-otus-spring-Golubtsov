package ru.otus.project.employee.controller.rest.crudl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.project.employee.controller.dto.EmployeeUpdatedDto;
import ru.otus.project.employee.dto.EmployeeDto;
import ru.otus.project.employee.service.EmployeeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeCrudlController {

    private final EmployeeService employeeService;


    @PostMapping("/employee")
    public EmployeeDto create(@RequestBody EmployeeUpdatedDto employeeUpdatedDto) {
        return employeeService.create(
                employeeUpdatedDto.personalInfoId(),
                employeeUpdatedDto.jobTitle(),
                employeeUpdatedDto.managerEmployeeId(),
                employeeUpdatedDto.departmentCode(),
                employeeUpdatedDto.officeId(),
                employeeUpdatedDto.additionalNumber(),
                employeeUpdatedDto.accountId()
        );
    }

    @GetMapping("/employee/{id}")
    public EmployeeDto read(@PathVariable long id) {
        return employeeService.findById(id);
    }

    @PutMapping("/employee/{id}")
    public EmployeeDto update(@PathVariable long id, @RequestBody EmployeeUpdatedDto employeeUpdatedDto) {
        return employeeService.update(
                id,
                employeeUpdatedDto.personalInfoId(),
                employeeUpdatedDto.jobTitle(),
                employeeUpdatedDto.managerEmployeeId(),
                employeeUpdatedDto.departmentCode(),
                employeeUpdatedDto.officeId(),
                employeeUpdatedDto.additionalNumber(),
                employeeUpdatedDto.accountId()
        );
    }

    @DeleteMapping("/employee/{id}")
    public void employeeDto(@PathVariable long id) {
        employeeService.delete(id);
    }

    @GetMapping("/employee")
    public List<EmployeeDto> list() {
        return employeeService.findAll();
    }

}
