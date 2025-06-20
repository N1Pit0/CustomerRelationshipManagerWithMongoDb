package com.mygym.crm.trainercontributioncalculator.service.mappers;

import com.mygym.crm.sharedmodule.TrainerWorkloadDto;
import com.mygym.crm.trainercontributioncalculator.domain.models.TrainerSummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrainerSummaryMapper {

    @Mapping(target = "username", source = "userName")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "monthlySummaries", ignore = true)
    TrainerSummary toTrainerSummary(TrainerWorkloadDto trainerWorkloadDto);
}

