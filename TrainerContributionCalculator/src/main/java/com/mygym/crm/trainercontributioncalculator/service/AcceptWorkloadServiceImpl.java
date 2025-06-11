package com.mygym.crm.trainercontributioncalculator.service;

import com.mygym.crm.sharedmodule.TrainerWorkloadDto;
import com.mygym.crm.trainercontributioncalculator.domain.models.MonthlySummary;
import com.mygym.crm.trainercontributioncalculator.domain.models.TrainerSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcceptWorkloadServiceImpl implements AcceptWorkload {
    private static final Logger LOGGER = LoggerFactory.getLogger(AcceptWorkloadServiceImpl.class);

    private final TrainerSummaryService trainerSummaryService;
    private final MonthlySummaryService monthlySummaryService;

    @Autowired
    public AcceptWorkloadServiceImpl(TrainerSummaryService trainerSummaryService, MonthlySummaryService monthlySummaryService) {
        this.trainerSummaryService = trainerSummaryService;
        this.monthlySummaryService = monthlySummaryService;
    }

    public void acceptWorkload(TrainerWorkloadDto trainerWorkloadDto) {
        LOGGER.info("Accepting workload for trainer: {}", trainerWorkloadDto.getUserName());
        switch (trainerWorkloadDto.getActionType()) {
            case ADD -> addTrainingHours(trainerWorkloadDto);
            case DELETE -> removeTrainingHours(trainerWorkloadDto);
        }
    }

    private boolean addTrainingHours(TrainerWorkloadDto trainerWorkloadDto) {
        LOGGER.debug("Adding training hours for trainer: {}", trainerWorkloadDto.getUserName());
        TrainerSummary trainerSummary = trainerSummaryService.findByUsername(trainerWorkloadDto.getUserName());

        if (trainerSummary == null) {
            LOGGER.debug("Creating new trainer summary for trainer: {}", trainerWorkloadDto.getUserName());
            trainerSummary = trainerSummaryService.createTrainerSummary(trainerWorkloadDto);
        }

        MonthlySummary monthlySummary = monthlySummaryService.findOrCreateMonthlySummary(trainerSummary, trainerWorkloadDto);
        monthlySummary.setTrainingDuration(monthlySummary.getTrainingDuration() + trainerWorkloadDto.getTrainingDuration());

        trainerSummary.getMonthlySummaries().add(monthlySummary);

        trainerSummaryService.updateTrainerSummary(trainerSummary);
        String trainerUsername = trainerWorkloadDto.getUserName();

        LOGGER.info("Training hours added successfully for trainer: {}", trainerUsername);
        LOGGER.debug("Training hours for trainer {} amounts to: {}", trainerUsername, monthlySummary.getTrainingDuration());
        return true;
    }

    private boolean removeTrainingHours(TrainerWorkloadDto trainerWorkloadDto) {
        LOGGER.debug("Removing training hours for trainer: {}", trainerWorkloadDto.getUserName());
        TrainerSummary trainerSummary = trainerSummaryService.findByUsername(trainerWorkloadDto.getUserName());

        if (trainerSummary == null) {
            LOGGER.warn("Trainer summary not found for trainer: {}", trainerWorkloadDto.getUserName());
            return false;
        }

        MonthlySummary monthlySummary = monthlySummaryService.findMonthlySummary(trainerSummary, trainerWorkloadDto);


        if (monthlySummary == null) {
            LOGGER.warn("Monthly summary not found for trainer: {}", trainerWorkloadDto.getUserName());
            return false;
        }

        int updatedDuration = monthlySummary.getTrainingDuration() - trainerWorkloadDto.getTrainingDuration();

        if (updatedDuration <= 0) {
            LOGGER.debug("Removing monthly summary for trainer: {}", trainerWorkloadDto.getUserName());
            trainerSummary.getMonthlySummaries().remove(monthlySummary);
        } else {
            LOGGER.debug("Updating training duration for trainer: {}", trainerWorkloadDto.getUserName());
            monthlySummary.setTrainingDuration(updatedDuration);
        }

        trainerSummaryService.updateTrainerSummary(trainerSummary);
        return true;
    }
}