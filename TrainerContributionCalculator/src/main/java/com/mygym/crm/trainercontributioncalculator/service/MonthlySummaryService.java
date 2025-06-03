package com.mygym.crm.trainercontributioncalculator.service;

import com.mygym.crm.sharedmodule.TrainerWorkloadDto;
import com.mygym.crm.trainercontributioncalculator.domain.models.MonthlySummary;
import com.mygym.crm.trainercontributioncalculator.domain.models.TrainerSummary;
import com.mygym.crm.trainercontributioncalculator.service.mappers.MonthlySummaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonthlySummaryService {
    private final MonthlySummaryMapper monthlySummaryMapper;

    @Autowired
    public MonthlySummaryService(MonthlySummaryMapper monthlySummaryMapper) {
        this.monthlySummaryMapper = monthlySummaryMapper;
    }

    public MonthlySummary findOrCreateMonthlySummary(TrainerSummary trainerSummary, TrainerWorkloadDto trainerWorkloadDto) {
        MonthlySummary monthlySummary = findMonthlySummary(trainerSummary, trainerWorkloadDto);

        if (monthlySummary == null) {
            monthlySummary = monthlySummaryMapper.toMonthlySummary(trainerWorkloadDto);
            monthlySummary.setTrainerSummary(trainerSummary);
            trainerSummary.getMonthlySummaries().add(monthlySummary);
        }

        return monthlySummary;
    }

    public MonthlySummary findMonthlySummary(TrainerSummary trainerSummary, TrainerWorkloadDto trainerWorkloadDto) {
        return trainerSummary.getMonthlySummaries().stream()
                .filter(summary -> summary.getSummaryYear() == trainerWorkloadDto.getTrainingDate().getYear())
                .filter(summary -> summary.getSummaryMonth() == monthlySummaryMapper.integerToEnum(trainerWorkloadDto.getTrainingDate().getMonthValue()))
                .findFirst()
                .orElse(null);
    }
}