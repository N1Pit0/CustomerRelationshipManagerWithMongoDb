package com.mygym.crm.trainercontributioncalculator.service.mappers;

import com.mygym.crm.sharedmodule.TrainerWorkloadDto;
import com.mygym.crm.trainercontributioncalculator.domain.models.MonthEnum;
import com.mygym.crm.trainercontributioncalculator.domain.models.MonthlySummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MonthlySummaryMapper {

    @Mapping(target = "summaryYear", expression = "java(trainerWorkloadDto.getTrainingDate().getYear())")
    @Mapping(target = "summaryMonth", expression = "java(integerToEnum(trainerWorkloadDto.getTrainingDate().getMonthValue()))")
    @Mapping(target = "trainingDuration", constant = "0")
    MonthlySummary toMonthlySummary(TrainerWorkloadDto trainerWorkloadDto);

    default MonthEnum integerToEnum(Integer integer) {
        return switch (integer) {
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