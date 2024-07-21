package ru.otus.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "work_info")
@Entity
public class WorkInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "job_title", nullable = false, length = 255)
    private String jobTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "head_id", nullable = true)
    @ToString.Exclude
    private Employee headEmployee;

    @ManyToOne
    @JoinColumn(name = "department_code", nullable = false)
    private Department department;

    @ManyToOne
    @JoinColumn(name = "office_id", nullable = true)
    private Office office;

    @Column(name = "additional_number", nullable = false)
    private int additionalNumber;

}
