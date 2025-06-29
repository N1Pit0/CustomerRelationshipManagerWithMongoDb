package com.mygym.crm.backstages.core.dtos.response.trainerdto.select;

import com.mygym.crm.backstages.core.dtos.response.trainingtypedto.select.SelectTrainingTypeDtoSet;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class SelectTrainerTrainingsDtoSet {

    private Set<SelectTrainerTrainingsDto> trainerTrainings = new HashSet<>();

    @Data
    @NoArgsConstructor
    public static class SelectTrainerTrainingsDto {

        private String trainingName;

        private LocalDate trainingDate;

        private SelectTrainingTypeDtoSet.SelectTrainingType trainingType;

        private Integer trainingDuration;

        private String traineeName;

    }
}
