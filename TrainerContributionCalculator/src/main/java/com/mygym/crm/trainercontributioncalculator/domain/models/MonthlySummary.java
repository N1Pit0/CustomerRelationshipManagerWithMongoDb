package com.mygym.crm.trainercontributioncalculator.domain.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "monthly_summary")
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"trainerSummary"})
public class MonthlySummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "monthly_summary_id")
    private Long id;

    @Column(name = "summary_year")
    private Integer summaryYear;

    @Enumerated(EnumType.STRING)
    private MonthEnum summaryMonth;

    @Column()
    private Integer trainingDuration;

    @ManyToOne
    @JoinColumn(name = "trainer_summary_id", nullable = false, referencedColumnName = "trainer_summary_id")
    private TrainerSummary trainerSummary;

}
