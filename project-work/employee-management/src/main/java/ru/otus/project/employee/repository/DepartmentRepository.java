package ru.otus.project.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.project.employee.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, String> { }
