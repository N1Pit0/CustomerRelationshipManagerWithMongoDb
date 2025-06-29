package com.mygym.crm.backstages.core.dtos.response.trainerdto.select;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mygym.crm.backstages.core.dtos.response.trainerdto.mapping.MapSelectTraineeDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectTrainerDto {

    private String firstName;

    private String lastName;

    private String trainingTypeName;

    private Boolean isActive;

    private Set<MapSelectTraineeDto> trainees = new HashSet<>();
}
