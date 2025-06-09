package com.mygym.crm.trainercontributioncalculator.service;

import com.mygym.crm.sharedmodule.TrainerWorkloadDto;
import com.mygym.crm.trainercontributioncalculator.domain.models.MonthlySummary;
import com.mygym.crm.trainercontributioncalculator.domain.models.TrainerSummary;

public interface MonthlySummaryService {
    MonthlySummary findOrCreateMonthlySummary(TrainerSummary trainerSummary, TrainerWorkloadDto trainerWorkloadDto);

    MonthlySummary findMonthlySummary(TrainerSummary trainerSummary, TrainerWorkloadDto trainerWorkloadDto);
}

