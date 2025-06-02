package com.mygym.crm.trainercontributioncalculator.service;

import com.mygym.crm.sharedmodule.TrainerWorkloadDto;
import com.mygym.crm.trainercontributioncalculator.domain.models.TrainerSummary;
import com.mygym.crm.trainercontributioncalculator.repositories.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AcceptWorkloadServiceImpl implements AcceptWorkload {

    private TrainingRepository trainingRepository;

    @Autowired
    public void setTrainingRepository(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    public boolean acceptWorkload(TrainerWorkloadDto trainerWorkloadDto) {

        return switch (trainerWorkloadDto.getActionType()) {
            case ADD -> addTrainingHours(trainerWorkloadDto);
            case DELETE -> removeTrainingHours(trainerWorkloadDto);
        };
    }

    private boolean addTrainingHours(TrainerWorkloadDto trainerWorkloadDto) {
        TrainerSummary trainerSummary = trainingRepository.findByUsername(trainerWorkloadDto.getUserName());
        trainerSummary.getMonthlySummaries().stream();
        return false;
    }

    private boolean removeTrainingHours(TrainerWorkloadDto trainerWorkloadDto) {
        return false;
    }
}
