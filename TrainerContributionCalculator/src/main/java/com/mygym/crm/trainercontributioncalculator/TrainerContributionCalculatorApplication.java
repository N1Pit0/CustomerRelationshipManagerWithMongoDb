package com.mygym.crm.trainercontributioncalculator;

import com.mygym.crm.trainercontributioncalculator.service.communication.Receiver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories
public class TrainerContributionCalculatorApplication{

    public static void main(String[] args) {
        var app = SpringApplication.run(TrainerContributionCalculatorApplication.class, args);

        var a = app.getBean("receiver", Receiver.class);

    }

}
