package com.mygym.crm.backstages.core.dtos.response.traineedto.select;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mygym.crm.backstages.core.dtos.response.trainingtypedto.select.SelectTrainingTypeDtoSet;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectTraineeTrainingsDtoSet {

    private Set<SelectTraineeTrainingsDto> traineeTrainings = new HashSet<>();

    @Data
    @NoArgsConstructor
    public static class SelectTraineeTrainingsDto {

        private String trainingName;

        private LocalDate trainingDate;

        private SelectTrainingTypeDtoSet.SelectTrainingType trainingType;

        private Integer trainingDuration;

        private String trainerName;

    }
}
