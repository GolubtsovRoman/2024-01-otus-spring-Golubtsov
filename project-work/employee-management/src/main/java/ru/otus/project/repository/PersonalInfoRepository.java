package ru.otus.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.project.model.PersonalInfo;

public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long> { }
