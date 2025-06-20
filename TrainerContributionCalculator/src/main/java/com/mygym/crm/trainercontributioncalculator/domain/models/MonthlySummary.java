package com.mygym.crm.trainercontributioncalculator.domain.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@NoArgsConstructor
@Data
public class MonthlySummary {

    @Id
    private String id;

    private Integer summaryYear;

    private MonthEnum summaryMonth;

    private Integer trainingDuration;

}
