package com.mygym.crm.trainercontributioncalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableMongoRepositories
public class TrainerContributionCalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainerContributionCalculatorApplication.class, args);
    }
}
