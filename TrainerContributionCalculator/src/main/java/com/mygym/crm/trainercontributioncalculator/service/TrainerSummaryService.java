package com.mygym.crm.trainercontributioncalculator.service;

import com.mygym.crm.sharedmodule.TrainerWorkloadDto;
import com.mygym.crm.trainercontributioncalculator.domain.models.TrainerSummary;
import com.mygym.crm.trainercontributioncalculator.repositories.TrainingRepository;
import com.mygym.crm.trainercontributioncalculator.service.mappers.TrainerSummaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrainerSummaryService {
    private final TrainingRepository trainingRepository;
    private final TrainerSummaryMapper trainerSummaryMapper;

    @Autowired
    public TrainerSummaryService(TrainingRepository trainingRepository, TrainerSummaryMapper trainerSummaryMapper) {
        this.trainingRepository = trainingRepository;
        this.trainerSummaryMapper = trainerSummaryMapper;
    }

    @Transactional
    public TrainerSummary createTrainerSummary(TrainerWorkloadDto trainerWorkloadDto) {
        TrainerSummary trainerSummary = trainerSummaryMapper.toTrainerSummary(trainerWorkloadDto);
        return trainingRepository.save(trainerSummary);
    }

    @Transactional
    public TrainerSummary updateTrainerSummary(TrainerSummary trainerSummary) {
        return trainingRepository.save(trainerSummary);
    }

    public TrainerSummary findByUsername(String username) {
        return trainingRepository.findByUsername(username);
    }
}