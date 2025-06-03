package com.mygym.crm.trainercontributioncalculator.service;

import com.mygym.crm.sharedmodule.TrainerWorkloadDto;
import com.mygym.crm.trainercontributioncalculator.domain.models.MonthEnum;
import com.mygym.crm.trainercontributioncalculator.domain.models.MonthlySummary;
import com.mygym.crm.trainercontributioncalculator.domain.models.TrainerSummary;
import com.mygym.crm.trainercontributioncalculator.repositories.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class AcceptWorkloadServiceImpl implements AcceptWorkload {

    private final TrainingRepository trainingRepository;

    @Autowired
    public AcceptWorkloadServiceImpl(TrainingRepository trainingRepository) {
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

        if (trainerSummary == null) {
            trainerSummary = createTrainerSummary(trainerWorkloadDto);
        }

        MonthlySummary monthlySummary = findOrCreateMonthlySummary(trainerSummary, trainerWorkloadDto);
        monthlySummary.setTrainingDuration(monthlySummary.getTrainingDuration() + trainerWorkloadDto.getTrainingDuration());

        trainerSummary.getMonthlySummaries().add(monthlySummary);
        trainingRepository.save(trainerSummary);
        return true;
    }

    private boolean removeTrainingHours(TrainerWorkloadDto trainerWorkloadDto) {
        TrainerSummary trainerSummary = trainingRepository.findByUsername(trainerWorkloadDto.getUserName());

        if (trainerSummary != null) {
            MonthlySummary monthlySummary = findMonthlySummary(trainerSummary, trainerWorkloadDto);

            if (monthlySummary != null) {
                int updatedDuration = monthlySummary.getTrainingDuration() - trainerWorkloadDto.getTrainingDuration();
                if (updatedDuration <= 0) {
                    trainerSummary.getMonthlySummaries().remove(monthlySummary);
                } else {
                    monthlySummary.setTrainingDuration(updatedDuration);
                }
                trainingRepository.save(trainerSummary);
                return true;
            }
        }
        return false;
    }

    private TrainerSummary createTrainerSummary(TrainerWorkloadDto trainerWorkloadDto) {
        TrainerSummary trainerSummary = new TrainerSummary();
        trainerSummary.setUsername(trainerWorkloadDto.getUserName());
        trainerSummary.setFirstName(trainerWorkloadDto.getFirstName());
        trainerSummary.setLastName(trainerWorkloadDto.getLastName());
        trainerSummary.setIsActive(trainerWorkloadDto.getIsActive());

        return trainingRepository.save(trainerSummary);
    }

    private MonthlySummary findOrCreateMonthlySummary(TrainerSummary trainerSummary, TrainerWorkloadDto trainerWorkloadDto) {
        MonthlySummary monthlySummary = findMonthlySummary(trainerSummary, trainerWorkloadDto);

        if (monthlySummary == null) {
            monthlySummary = new MonthlySummary();
            monthlySummary.setSummaryYear(trainerWorkloadDto.getTrainingDate().getYear());
            monthlySummary.setSummaryMonth(integerToEnum(trainerWorkloadDto.getTrainingDate().getMonthValue()));
            monthlySummary.setTrainingDuration(0);
            monthlySummary.setTrainerSummary(trainerSummary);
        }

        return monthlySummary;
    }

    private MonthlySummary findMonthlySummary(TrainerSummary trainerSummary, TrainerWorkloadDto trainerWorkloadDto) {
        return trainerSummary.getMonthlySummaries().stream()
                .filter(summary -> summary.getSummaryYear() == trainerWorkloadDto.getTrainingDate().getYear())
                .filter(summary -> summary.getSummaryMonth() == integerToEnum(trainerWorkloadDto.getTrainingDate().getMonthValue()))
                .findFirst()
                .orElse(null);
    }

    private MonthEnum integerToEnum(Integer integer) {
        return switch (integer){
            case 1 -> MonthEnum.JANUARY;
            case 2 -> MonthEnum.FEBRUARY;
            case 3 -> MonthEnum.MARCH;
            case 4 -> MonthEnum.APRIL;
            case 5 -> MonthEnum.May;
            case 6 -> MonthEnum.JUNE;
            case 7 -> MonthEnum.JULY;
            case 8 -> MonthEnum.AUGUST;
            case 9 -> MonthEnum.SEPTEMBER;
            case 10 -> MonthEnum.OCTOBER;
            case 11 -> MonthEnum.NOVEMBER;
            case 12 -> MonthEnum.DECEMBER;
            default -> null;
        };
    }
}
