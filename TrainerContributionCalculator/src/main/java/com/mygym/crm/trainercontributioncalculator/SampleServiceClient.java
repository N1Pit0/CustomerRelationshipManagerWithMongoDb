package com.mygym.crm.trainercontributioncalculator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface SampleServiceClient {
    @GetMapping("/greeting")
    String greeting();
}