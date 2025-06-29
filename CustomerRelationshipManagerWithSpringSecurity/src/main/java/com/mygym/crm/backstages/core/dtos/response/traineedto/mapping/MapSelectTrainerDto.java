package com.mygym.crm.backstages.core.dtos.response.traineedto.mapping;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MapSelectTrainerDto {

    private String userName;

    private String firstName;

    private String lastName;

    private String trainingTypeName;
}
