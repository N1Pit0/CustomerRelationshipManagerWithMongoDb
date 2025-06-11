package com.mygym.crm.trainercontributioncalculator.service;

import com.mygym.crm.sharedmodule.TrainerWorkloadDto;
import com.mygym.crm.trainercontributioncalculator.domain.models.TrainerSummary;
import com.mygym.crm.trainercontributioncalculator.repositories.TrainingRepository;
import com.mygym.crm.trainercontributioncalculator.service.mappers.TrainerSummaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrainerSummaryServiceImpl implements TrainerSummaryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerSummaryServiceImpl.class);

    private final TrainingRepository trainingRepository;
    private final TrainerSummaryMapper trainerSummaryMapper;

    @Autowired
    public TrainerSummaryServiceImpl(TrainingRepository trainingRepository, TrainerSummaryMapper trainerSummaryMapper) {
        this.trainingRepository = trainingRepository;
        this.trainerSummaryMapper = trainerSummaryMapper;
    }

    @Transactional
    @Override
    public TrainerSummary createTrainerSummary(TrainerWorkloadDto trainerWorkloadDto) {
        LOGGER.debug("Creating trainer summary for trainer: {}", trainerWorkloadDto.getUserName());
        TrainerSummary trainerSummary = trainerSummaryMapper.toTrainerSummary(trainerWorkloadDto);
        LOGGER.debug("created trainer summary: {}", trainerSummary);
        TrainerSummary savedTrainerSummary = trainingRepository.save(trainerSummary); // The exception is thrown here!!!
        LOGGER.info("Trainer summary created for trainer: {}", savedTrainerSummary.getUsername());
        return savedTrainerSummary;
    }

    @Transactional
    @Override
    public TrainerSummary updateTrainerSummary(TrainerSummary trainerSummary) {
        LOGGER.debug("Updating trainer summary for trainer: {}", trainerSummary.getUsername());
        TrainerSummary updatedTrainerSummary = trainingRepository.save(trainerSummary);
        LOGGER.info("Trainer summary updated for trainer: {}", updatedTrainerSummary.getUsername());
        return updatedTrainerSummary;
    }

    @Transactional(readOnly = true)
    @Override
    public TrainerSummary findByUsername(String username) {
        LOGGER.debug("Finding trainer summary by username: {}", username);
        TrainerSummary trainerSummary = trainingRepository.findByUsername(username);
        if (trainerSummary != null) {
            LOGGER.debug("Trainer summary found for username: {}", username);
        } else {
            LOGGER.debug("Trainer summary not found for username: {}", username);
        }
        return trainerSummary;
    }
}