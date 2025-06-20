package com.mygym.crm.trainercontributioncalculator;

import com.mygym.crm.sharedmodule.TrainerWorkloadDto;
import com.mygym.crm.trainercontributioncalculator.service.AcceptWorkload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);
    private AcceptWorkload acceptWorkload;

    @Autowired
    public void setAcceptWorkload(AcceptWorkload acceptWorkload) {
        this.acceptWorkload = acceptWorkload;
    }

    @GetMapping("/test")
    public ResponseEntity<String> testController(@RequestBody TrainerWorkloadDto trainerWorkloadDto) {
        LOGGER.info("Testing workload : {}", trainerWorkloadDto);
        if (acceptWorkload.acceptWorkload(trainerWorkloadDto)) {
            LOGGER.info("Workload accepted");
            return new ResponseEntity<>("Workload accepted", HttpStatus.OK);
        }

        LOGGER.warn("Workload not accepted");
        return new ResponseEntity<>("Workload not accepted", HttpStatus.BAD_REQUEST);

    }
}
