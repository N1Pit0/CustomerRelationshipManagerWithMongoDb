package com.mygym.crm.backstages.core.dtos.response.trainingtypedto.select;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectTrainingTypeDtoSet {

    private Set<SelectTrainingType> trainingTypes = new HashSet<>();

    @Data
    public static class SelectTrainingType {

        private Long trainingTypeId;

        private String trainingTypeName;
    }
}
