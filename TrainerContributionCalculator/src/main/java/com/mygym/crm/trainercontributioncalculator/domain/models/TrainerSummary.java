package com.mygym.crm.trainercontributioncalculator.domain.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private String username;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, name = "status")
    private Boolean isActive;

    @OneToMany(mappedBy = "trainerSummary", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MonthlySummary> monthlySummaries = new HashSet<>();

}
