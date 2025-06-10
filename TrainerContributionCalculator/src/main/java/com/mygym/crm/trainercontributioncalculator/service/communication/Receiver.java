package com.mygym.crm.trainercontributioncalculator.service.communication;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @JmsListener(destination = "queue1")
    public void receiveMessage(Object message) {

        throw new RuntimeException("test exception");
    }
}
