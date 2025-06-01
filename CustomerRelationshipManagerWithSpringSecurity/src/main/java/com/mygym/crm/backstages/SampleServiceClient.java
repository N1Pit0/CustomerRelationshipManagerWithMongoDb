package com.mygym.crm.backstages;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@FeignClient("trainer-contribution-calculator")
@RestController
public interface SampleServiceClient {
    @GetMapping("/greeting")
    String greeting();
}
