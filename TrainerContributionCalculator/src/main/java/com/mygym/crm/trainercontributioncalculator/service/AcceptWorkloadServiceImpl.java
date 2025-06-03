package com.mygym.crm.trainercontributioncalculator.service;

import com.mygym.crm.sharedmodule.TrainerWorkloadDto;
import com.mygym.crm.trainercontributioncalculator.domain.models.MonthlySummary;
import com.mygym.crm.trainercontributioncalculator.domain.models.TrainerSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AcceptWorkloadServiceImpl implements AcceptWorkload {
    private final TrainerSummaryService trainerSummaryService;
    private final MonthlySummaryService monthlySummaryService;

    @Autowired
    public AcceptWorkloadServiceImpl(TrainerSummaryService trainerSummaryService, MonthlySummaryService monthlySummaryService) {
        this.trainerSummaryService = trainerSummaryService;
        this.monthlySummaryService = monthlySummaryService;
    }

    @Transactional
    public boolean acceptWorkload(TrainerWorkloadDto trainerWorkloadDto) {
        return switch (trainerWorkloadDto.getActionType()) {
            case ADD -> addTrainingHours(trainerWorkloadDto);
            case DELETE -> removeTrainingHours(trainerWorkloadDto);
        };
    }

    private boolean addTrainingHours(TrainerWorkloadDto trainerWorkloadDto) {
        TrainerSummary trainerSummary = trainerSummaryService.findByUsername(trainerWorkloadDto.getUserName());

        if (trainerSummary == null) {
            trainerSummary = trainerSummaryService.createTrainerSummary(trainerWorkloadDto);
        }

        MonthlySummary monthlySummary = monthlySummaryService.findOrCreateMonthlySummary(trainerSummary, trainerWorkloadDto);
        monthlySummary.setTrainingDuration(monthlySummary.getTrainingDuration() + trainerWorkloadDto.getTrainingDuration());

        trainerSummary.getMonthlySummaries().add(monthlySummary);

        trainerSummaryService.updateTrainerSummary(trainerSummary);
        return true;
    }

    private boolean removeTrainingHours(TrainerWorkloadDto trainerWorkloadDto) {
        TrainerSummary trainerSummary = trainerSummaryService.findByUsername(trainerWorkloadDto.getUserName());

        if (trainerSummary != null) {
            MonthlySummary monthlySummary = monthlySummaryService.findMonthlySummary(trainerSummary, trainerWorkloadDto);

            if (monthlySummary != null) {
                int updatedDuration = monthlySummary.getTrainingDuration() - trainerWorkloadDto.getTrainingDuration();
                if (updatedDuration <= 0) {
                    trainerSummary.getMonthlySummaries().remove(monthlySummary);
                } else {
                    monthlySummary.setTrainingDuration(updatedDuration);
                }
                trainerSummaryService.updateTrainerSummary(trainerSummary);
            }
        }
        return false;
    }
}