package ru.otus.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.project.model.Department;
import ru.otus.project.model.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findEmployeeByOfficeId(long officeId);

    @Query("SELECT e FROM Employee e WHERE e.office IS null")
    List<Employee> findEmployeeWhereOfficeIdIsNull();

    List<Employee> findEmployeeByDepartment(Department department);

}
