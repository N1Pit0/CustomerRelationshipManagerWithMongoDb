package com.mygym.crm.sharedmodule;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface MyClient {
    @PutMapping("trainer-hours")
    ResponseEntity<Void> acceptWorkload(@RequestBody TrainerWorkloadDto trainerWorkloadDto);
}
