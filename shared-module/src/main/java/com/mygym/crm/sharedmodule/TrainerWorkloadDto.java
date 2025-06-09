package com.mygym.crm.sharedmodule;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class TrainerWorkloadDto implements Serializable {
    private String userName;

    private String firstName;

    private String lastName;

    private Boolean isActive;

    private LocalDate trainingDate;

    private Integer trainingDuration;

    private ActionEnum actionType;


}
