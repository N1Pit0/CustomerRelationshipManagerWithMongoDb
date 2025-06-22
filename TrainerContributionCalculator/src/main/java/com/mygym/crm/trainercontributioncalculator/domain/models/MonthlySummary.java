package com.mygym.crm.trainercontributioncalculator.domain.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"trainingDuration"})
public class MonthlySummary {

    private Integer summaryYear;

    private MonthEnum summaryMonth;

    private Integer trainingDuration;

}
