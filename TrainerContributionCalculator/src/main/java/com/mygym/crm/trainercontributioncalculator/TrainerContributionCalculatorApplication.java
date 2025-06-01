package com.mygym.crm.trainercontributioncalculator;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Lazy;


@SpringBootApplication
@EnableDiscoveryClient
public class TrainerContributionCalculatorApplication implements SampleServiceClient{

    private EurekaClient eurekaClient;

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    @Lazy
    public void setEurekaClient(EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
    }


    public static void main(String[] args) {
        SpringApplication.run(TrainerContributionCalculatorApplication.class, args);
    }


    @Override
    public String greeting() {
        return String.format(
                "Hello from '%s'!", eurekaClient.getApplication(appName).getName());
    }

}
