package com.mygym.crm.trainercontributioncalculator.communication;

import com.mygym.crm.sharedmodule.TrainerWorkloadDto;
import com.mygym.crm.trainercontributioncalculator.service.AcceptWorkload;
import com.mygym.crm.trainercontributioncalculator.service.communication.TrainerContributionQueueReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TrainerContributionQueueReceiverImpl implements TrainerContributionQueueReceiver {

    private final AcceptWorkload acceptWorkloadService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerContributionQueueReceiverImpl.class);

    @Autowired
    public TrainerContributionQueueReceiverImpl(AcceptWorkload acceptWorkloadService) {
        this.acceptWorkloadService = acceptWorkloadService;
    }

    @JmsListener(destination = TRAINER_CONTRIBUTION_CALCULATOR_QUEUE)
    public void receiveMessage(TrainerWorkloadDto  message) {
        LOGGER.info("Receiving message from trainer contribution calculator queue");

        LOGGER.debug(message.toString());

        acceptWorkloadService.acceptWorkload(message);
    }
}
