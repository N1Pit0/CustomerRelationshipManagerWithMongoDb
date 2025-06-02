package com.mygym.crm.trainercontributioncalculator.controller;

import com.mygym.crm.sharedmodule.MyClient;
import com.mygym.crm.sharedmodule.TrainerWorkloadDto;
import com.mygym.crm.trainercontributioncalculator.service.AcceptWorkload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrainingController implements MyClient {

    private AcceptWorkload acceptWorkload;

    @Autowired
    public void setAcceptWorkload(AcceptWorkload acceptWorkload) {
        this.acceptWorkload = acceptWorkload;
    }

    @Override
    public ResponseEntity<Void> acceptWorkload(TrainerWorkloadDto trainerWorkloadDto) {

        return acceptWorkload.acceptWorkload(trainerWorkloadDto)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}
