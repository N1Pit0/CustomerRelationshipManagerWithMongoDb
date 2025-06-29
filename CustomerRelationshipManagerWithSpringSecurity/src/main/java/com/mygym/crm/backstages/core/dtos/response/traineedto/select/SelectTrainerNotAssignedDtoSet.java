package com.mygym.crm.backstages.core.dtos.response.traineedto.select;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mygym.crm.backstages.core.dtos.response.traineedto.mapping.MapSelectTrainerDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectTrainerNotAssignedDtoSet {

    private Set<MapSelectTrainerDto> notAssignedTrainers = new HashSet<>();

}