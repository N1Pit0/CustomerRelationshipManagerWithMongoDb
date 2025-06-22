package com.mygym.crm.trainercontributioncalculator;

import com.mygym.crm.sharedmodule.ActionEnum;
import com.mygym.crm.sharedmodule.TrainerWorkloadDto;
import com.mygym.crm.trainercontributioncalculator.service.AcceptWorkload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDate;


@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableMongoRepositories
public class TrainerContributionCalculatorApplication implements CommandLineRunner {

    private AcceptWorkload acceptWorkload;

    public static void main(String[] args) {
        SpringApplication.run(TrainerContributionCalculatorApplication.class, args);
    }

    @Autowired
    public void setAcceptWorkload(AcceptWorkload acceptWorkload) {
        this.acceptWorkload = acceptWorkload;
    }

    @Override
    public void run(String... args) throws Exception {

        var trainerWorkLoadDto = new TrainerWorkloadDto();

        trainerWorkLoadDto.setUserName("Nika.Maca");
        trainerWorkLoadDto.setFirstName("Nika");
        trainerWorkLoadDto.setLastName("Maca");
        trainerWorkLoadDto.setIsActive(true);
        trainerWorkLoadDto.setTrainingDate(LocalDate.now());
        trainerWorkLoadDto.setTrainingDuration(60);
        trainerWorkLoadDto.setActionType(ActionEnum.DELETE);

        acceptWorkload.acceptWorkload(trainerWorkLoadDto);
    }
}
