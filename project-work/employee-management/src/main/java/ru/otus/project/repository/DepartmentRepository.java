package ru.otus.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.project.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, String> { }