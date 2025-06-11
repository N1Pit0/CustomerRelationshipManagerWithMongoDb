package com.mygym.crm.trainercontributioncalculator.service.communication;

import org.springframework.jms.annotation.JmsListener;

public interface ReceiveFromTrainerContributionCalculatorQueue {

    String TRAINER_CONTRIBUTION_CALCULATOR_QUEUE = "TRAINER_CONTRIBUTION_CALCULATOR_QUEUE";

    @JmsListener(destination = TRAINER_CONTRIBUTION_CALCULATOR_QUEUE)
    void receiveMessage(Object message);

}
