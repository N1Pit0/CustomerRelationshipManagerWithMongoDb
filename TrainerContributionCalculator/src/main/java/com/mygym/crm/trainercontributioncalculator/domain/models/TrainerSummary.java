package com.mygym.crm.trainercontributioncalculator.domain.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "trainer_summary")
@NoArgsConstructor
@Data
public class TrainerSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, name = "trainer_summary_id")
    private Long trainerSummaryId;

    @Column(nullable = false, unique = true)
    private String Username;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, name = "status")
    private Boolean isActive;

    @OneToMany(mappedBy = "trainerSummary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MonthlySummary> monthlySummaries;

}
