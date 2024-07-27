package ru.otus.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.project.dto.EmployeeDto;
import ru.otus.project.exception.EntityNotFoundException;
import ru.otus.project.model.Department;
import ru.otus.project.model.Employee;
import ru.otus.project.model.Office;
import ru.otus.project.model.PersonalInfo;
import ru.otus.project.repository.DepartmentRepository;
import ru.otus.project.repository.EmployeeRepository;
import ru.otus.project.repository.OfficeRepository;
import ru.otus.project.repository.PersonalInfoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final PersonalInfoRepository personalInfoRepository;

    private final DepartmentRepository departmentRepository;

    private final OfficeRepository officeRepository;


    @Transactional
    public EmployeeDto create(long personalInfoId,
                              String jobTitle,
                              Long managerId,
                              String departmentCode,
                              Long officeId,
                              Integer additionalNumber,
                              String accountId) {
        Employee employee = new Employee(
                0,
                personalInfoById(personalInfoId),
                jobTitle,
                employeeByIdOrNull(managerId),
                departmentByCodeOrNull(departmentCode),
                officeByIdOrNull(officeId),
                additionalNumber,
                accountId
        );

        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeDto.fromEntity(savedEmployee);
    }

    @Transactional(readOnly = true)
    public EmployeeDto findById(long id) {
        return employeeRepository.findById(id)
                .map(EmployeeDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Employee with id=%d not found".formatted(id)));
    }

    @Transactional
    public EmployeeDto update(long id,
                              long personalInfoId,
                              String jobTitle,
                              Long managerId,
                              String departmentCode,
                              Long officeId,
                              Integer additionalNumber,
                              String accountId) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee with id=%d not found".formatted(id)));

        employee.setPersonalInfo(personalInfoById(personalInfoId));
        employee.setJobTitle(jobTitle);
        employee.setManagerEmployee(employeeByIdOrNull(managerId));
        employee.setDepartment(departmentByCodeOrNull(departmentCode));
        employee.setOffice(officeByIdOrNull(officeId));
        employee.setAdditionalNumber(additionalNumber);
        employee.setAccountId(accountId);

        Employee updatedEmployee = employeeRepository.save(employee);
        return EmployeeDto.fromEntity(updatedEmployee);
    }

    @Transactional
    public void delete(long id) {
        employeeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDto> findAll() {
        return employeeRepository.findAll()
                .stream().map(EmployeeDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public EmployeeDto findManagerById(long id) {
        Employee managerEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee with id=%d not found".formatted(id)))
                .getManagerEmployee();

        if (managerEmployee == null) {
            return null;
        }
        return EmployeeDto.fromEntity(managerEmployee);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDto> findAllInOffice(long officeId) {
        return employeeRepository.findEmployeeByOfficeId(officeId)
                .stream()
                .map(EmployeeDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EmployeeDto> findAllRemote() {
        return employeeRepository.findEmployeeWhereOfficeIdIsNull()
                .stream()
                .map(EmployeeDto::fromEntity)
                .toList();
    }


    private PersonalInfo personalInfoById(long id) {
        return personalInfoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PersonalInfo with id=%d not found".formatted(id)));
    }

    private Employee employeeByIdOrNull(Long id) {
        if (id != null) {
            return employeeRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Employee with id=%d not found".formatted(id)));
        } else {
            return null;
        }
    }

    private Department departmentByCodeOrNull(String code) {
        if (code != null) {
            return departmentRepository.findById(code)
                    .orElseThrow(() -> new EntityNotFoundException("Department with code=%s not found".formatted(code)));
        } else {
            return null;
        }
    }

    private Office officeByIdOrNull(Long id) {
        if (id != null) {
            return officeRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Office with id=%d not found".formatted(id)));
        } else  {
            return null;
        }
    }

}
