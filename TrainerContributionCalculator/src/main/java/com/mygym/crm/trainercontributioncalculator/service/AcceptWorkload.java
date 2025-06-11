package com.mygym.crm.trainercontributioncalculator.service;

import com.mygym.crm.sharedmodule.TrainerWorkloadDto;

public interface AcceptWorkload {
    boolean acceptWorkload(TrainerWorkloadDto trainerWorkloadDto);
}
