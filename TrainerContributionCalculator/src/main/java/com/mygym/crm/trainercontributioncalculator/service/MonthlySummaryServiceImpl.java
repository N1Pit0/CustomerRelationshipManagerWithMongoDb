package com.mygym.crm.trainercontributioncalculator.service;

import com.mygym.crm.sharedmodule.TrainerWorkloadDto;
import com.mygym.crm.trainercontributioncalculator.domain.models.MonthlySummary;
import com.mygym.crm.trainercontributioncalculator.domain.models.TrainerSummary;
import com.mygym.crm.trainercontributioncalculator.service.mappers.MonthlySummaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonthlySummaryServiceImpl implements MonthlySummaryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonthlySummaryServiceImpl.class);
    private final MonthlySummaryMapper monthlySummaryMapper;

    @Autowired
    public MonthlySummaryServiceImpl(MonthlySummaryMapper monthlySummaryMapper) {
        this.monthlySummaryMapper = monthlySummaryMapper;
    }

    @Override
    public MonthlySummary findOrCreateMonthlySummary(TrainerSummary trainerSummary, TrainerWorkloadDto trainerWorkloadDto) {
        LOGGER.debug("Finding or creating monthly summary for trainer: {}", trainerSummary.getUsername());
        MonthlySummary monthlySummary = findMonthlySummary(trainerSummary, trainerWorkloadDto);

        if (monthlySummary == null) {
            monthlySummary = monthlySummaryMapper.toMonthlySummary(trainerWorkloadDto);
            trainerSummary.getMonthlySummaries().add(monthlySummary);
            LOGGER.info("New monthly summary created for trainer: {}", trainerSummary.getUsername());
        }

        return monthlySummary;
    }

    @Override
    public MonthlySummary findMonthlySummary(TrainerSummary trainerSummary, TrainerWorkloadDto trainerWorkloadDto) {
        LOGGER.debug("Finding monthly summary for trainer: {}", trainerSummary.getUsername());
        MonthlySummary monthlySummary = trainerSummary.getMonthlySummaries().stream()
                .filter(summary -> summary.getSummaryYear() == trainerWorkloadDto.getTrainingDate().getYear())
                .filter(summary -> summary.getSummaryMonth() == monthlySummaryMapper.integerToEnum(trainerWorkloadDto.getTrainingDate().getMonthValue()))
                .findFirst()
                .orElse(null);

        if (monthlySummary != null) {
            LOGGER.debug("Monthly summary found for trainer: {}", trainerSummary.getUsername());
        } else {
            LOGGER.debug("Monthly summary not found for trainer: {}", trainerSummary.getUsername());
        }

        return monthlySummary;
    }
}