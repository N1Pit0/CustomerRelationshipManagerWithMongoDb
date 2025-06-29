package com.mygym.crm.backstages.core.dtos.response.trainerdto.mapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MapUpdateTraineeDto {

    private String userName;

    private String firstName;

    private String lastName;
}
