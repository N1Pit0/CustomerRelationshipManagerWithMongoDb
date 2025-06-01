package com.mygym.crm.backstages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@EnableJpaRepositories("com.mygym.crm.backstages")
@EnableFeignClients
public class Application {

    SampleServiceClient sampleServiceClient;

    @Autowired
    public void setSampleServiceClient(SampleServiceClient sampleServiceClient) {
        this.sampleServiceClient = sampleServiceClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

    @GetMapping("/get-greeting")
    public String greeting(Model model) {
        model.addAttribute("greeting", sampleServiceClient.greeting());
        return "greeting-view";
    }
}
