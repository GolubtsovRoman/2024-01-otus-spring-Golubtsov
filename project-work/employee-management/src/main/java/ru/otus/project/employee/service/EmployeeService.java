package ru.otus.project.employee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.project.employee.dto.EmployeeDto;
import ru.otus.project.employee.exception.EntityNotFoundException;
import ru.otus.project.employee.model.Department;
import ru.otus.project.employee.model.Employee;
import ru.otus.project.employee.model.Office;
import ru.otus.project.employee.model.PersonalInfo;
import ru.otus.project.employee.repository.DepartmentRepository;
import ru.otus.project.employee.repository.EmployeeRepository;
import ru.otus.project.employee.repository.OfficeRepository;
import ru.otus.project.employee.repository.PersonalInfoRepository;
import ru.otus.project.employee.service.http.AccountProviderHttpClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final PersonalInfoRepository personalInfoRepository;

    private final DepartmentRepository departmentRepository;

    private final OfficeRepository officeRepository;

    private final AccountProviderHttpClient accountProviderHttpClient;


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
                managerId,
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
        employee.setManagerId(managerId);
        employee.setDepartment(departmentByCodeOrNull(departmentCode));
        employee.setOffice(officeByIdOrNull(officeId));
        employee.setAdditionalNumber(additionalNumber);
        employee.setAccountId(accountId);

        Employee updatedEmployee = employeeRepository.save(employee);
        return EmployeeDto.fromEntity(updatedEmployee);
    }

    @Transactional
    public void delete(long id) {
        String accountId = employeeByIdOrNull(id).getAccountId();
        accountProviderHttpClient.deleteRequest(accountId);
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
        Long managerId = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee with id=%d not found".formatted(id)))
                .getManagerId();

        if (managerId == null) {
            return null;
        }
        Employee managerEmployee = employeeRepository.findById(managerId)
                .orElseThrow(() -> new EntityNotFoundException("Employee with id=%d not found".formatted(managerId)));
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
