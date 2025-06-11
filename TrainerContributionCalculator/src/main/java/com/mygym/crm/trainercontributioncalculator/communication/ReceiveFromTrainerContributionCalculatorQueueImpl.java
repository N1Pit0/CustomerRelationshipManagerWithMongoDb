package com.mygym.crm.trainercontributioncalculator.communication;

import com.mygym.crm.sharedmodule.TrainerWorkloadDto;
import com.mygym.crm.trainercontributioncalculator.service.AcceptWorkload;
import com.mygym.crm.trainercontributioncalculator.service.communication.ReceiveFromTrainerContributionCalculatorQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ReceiveFromTrainerContributionCalculatorQueueImpl implements ReceiveFromTrainerContributionCalculatorQueue {

    private final AcceptWorkload acceptWorkloadService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiveFromTrainerContributionCalculatorQueueImpl.class);

    @Autowired
    public ReceiveFromTrainerContributionCalculatorQueueImpl(AcceptWorkload acceptWorkloadService) {
        this.acceptWorkloadService = acceptWorkloadService;
    }

    @JmsListener(destination = TRAINER_CONTRIBUTION_CALCULATOR_QUEUE)
    public void receiveMessage(Object message) {
        LOGGER.info("Receiving message from trainer contribution calculator queue");

        if (message instanceof TrainerWorkloadDto trainerWorkloadDto) {
            acceptWorkloadService.acceptWorkload(trainerWorkloadDto);
        }
        else LOGGER.warn("The message is not a TrainerWorkloadDto instance");
    }
}
