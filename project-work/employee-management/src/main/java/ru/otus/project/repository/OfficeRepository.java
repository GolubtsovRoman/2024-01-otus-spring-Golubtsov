package ru.otus.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.project.model.Office;

public interface OfficeRepository extends JpaRepository<Office, Long> { }
