package com.mygym.crm.backstages;

import com.mygym.crm.backstages.core.services.communication.Sender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.mygym.crm.backstages")
@EnableFeignClients
public class Application {


    public static void main(String[] args) {
        var app = SpringApplication.run(Application.class, args);

        var send = app.getBean("Sender", Sender.class);

        send.sendMessage("queue1", new TestDto("test", 1));

    }

}
