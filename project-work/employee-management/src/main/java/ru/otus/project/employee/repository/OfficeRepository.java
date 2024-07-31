package ru.otus.project.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.project.employee.model.Office;

public interface OfficeRepository extends JpaRepository<Office, Long> { }
