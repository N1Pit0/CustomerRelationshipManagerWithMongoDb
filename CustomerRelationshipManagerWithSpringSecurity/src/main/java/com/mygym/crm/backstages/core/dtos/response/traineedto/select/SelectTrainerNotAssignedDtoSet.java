package com.mygym.crm.backstages.core.dtos.response.traineedto.select;

import com.mygym.crm.backstages.core.dtos.response.traineedto.mapping.MapSelectTrainerDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class SelectTrainerNotAssignedDtoSet {

    private Set<MapSelectTrainerDto> notAssignedTrainers = new HashSet<>();

}