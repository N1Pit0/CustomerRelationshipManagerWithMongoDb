package com.mygym.crm.sharedmodule;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class TrainerWorkloadDto implements Serializable {

    @JsonCreator
    public TrainerWorkloadDto(@JsonProperty("userName") String userName, @JsonProperty("firstName") String firstName,
                              @JsonProperty("lastName") String lastName, @JsonProperty("isActive") Boolean isActive,
                              @JsonProperty("trainingDate") LocalDate trainingDate, @JsonProperty("trainingDuration") Integer trainingDuration,
                              @JsonProperty("actionType") ActionEnum actionType) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = isActive;
        this.trainingDate = trainingDate;
        this.trainingDuration = trainingDuration;
        this.actionType = actionType;
    }

    public TrainerWorkloadDto(){}

    private String userName;

    private String firstName;

    private String lastName;

    private Boolean isActive;

    private LocalDate trainingDate;

    private Integer trainingDuration;

    private ActionEnum actionType;


}
