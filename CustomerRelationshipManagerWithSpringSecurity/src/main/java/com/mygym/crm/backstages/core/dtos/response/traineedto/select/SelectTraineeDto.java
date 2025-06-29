package com.mygym.crm.backstages.core.dtos.response.traineedto.select;

import com.mygym.crm.backstages.core.dtos.response.traineedto.mapping.MapSelectTrainerDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class SelectTraineeDto {
    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String address;

    private Boolean isActive;

    private Set<MapSelectTrainerDto> trainers = new HashSet<>();
}
