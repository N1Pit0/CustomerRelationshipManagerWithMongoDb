package com.mygym.crm.trainercontributioncalculator.service;

import com.mygym.crm.sharedmodule.TrainerWorkloadDto;
import com.mygym.crm.trainercontributioncalculator.domain.models.TrainerSummary;
import com.mygym.crm.trainercontributioncalculator.repositories.TrainerSummaryRepository;
import com.mygym.crm.trainercontributioncalculator.service.mappers.TrainerSummaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerSummaryServiceImpl implements TrainerSummaryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerSummaryServiceImpl.class);

    private final TrainerSummaryRepository trainerSummaryRepository;
    private final TrainerSummaryMapper trainerSummaryMapper;

    @Autowired
    public TrainerSummaryServiceImpl(TrainerSummaryRepository trainerSummaryRepository, TrainerSummaryMapper trainerSummaryMapper) {
        this.trainerSummaryRepository = trainerSummaryRepository;
        this.trainerSummaryMapper = trainerSummaryMapper;
    }

    @Override
    public TrainerSummary createTrainerSummary(TrainerWorkloadDto trainerWorkloadDto) {
        LOGGER.debug("Mapping trainerWorkloadDto to trainerSummary for trainer: {}", trainerWorkloadDto.getUserName());
        TrainerSummary trainerSummary = trainerSummaryMapper.toTrainerSummary(trainerWorkloadDto);
        LOGGER.debug("Mapped trainerWorkloadDto to trainerSummary as : {}", trainerSummary);
        LOGGER.info("Saving new trainerSummary: {}", trainerSummary);
        TrainerSummary savedTrainerSummary = trainerSummaryRepository.save(trainerSummary); // The exception is thrown here!!!
        LOGGER.info("Trainer summary created for trainer: {}", savedTrainerSummary.getUsername());
        return savedTrainerSummary;
    }

    @Override
    public TrainerSummary updateTrainerSummary(TrainerSummary trainerSummary) {
        LOGGER.debug("Updating trainer summary for trainer: {}", trainerSummary.getUsername());
        TrainerSummary updatedTrainerSummary = trainerSummaryRepository.save(trainerSummary);
        LOGGER.info("Trainer summary updated for trainer: {}", updatedTrainerSummary.getUsername());
        return updatedTrainerSummary;
    }

    @Override
    public TrainerSummary findByUsername(String username) {
        LOGGER.debug("Finding trainer summary by username: {}", username);
        TrainerSummary trainerSummary = trainerSummaryRepository.findByUsername(username);
        if (trainerSummary != null) {
            LOGGER.debug("Trainer summary found for username: {}", username);
            trainerSummary.getMonthlySummaries().size();
        } else {
            LOGGER.debug("Trainer summary not found for username: {}", username);
        }
        return trainerSummary;
    }
}