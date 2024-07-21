package ru.otus.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.project.model.WorkInfo;

public interface WorkInfoRepository extends JpaRepository<WorkInfo, Long> { }
