package com.mygym.crm.backstages.core.services;

import com.mygym.crm.sharedmodule.MyClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "trainer-contribution-calculator")
public interface TrainerHoursCalculator extends MyClient {
}
