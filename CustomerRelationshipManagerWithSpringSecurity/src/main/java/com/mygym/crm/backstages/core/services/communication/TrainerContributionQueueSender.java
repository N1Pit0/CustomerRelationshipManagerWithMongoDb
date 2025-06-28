package com.mygym.crm.backstages.core.services.communication;

import com.mygym.crm.sharedmodule.TrainerWorkloadDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FunctionalInterface
public interface TrainerContributionQueueSender {

    String TRAINER_CONTRIBUTION_CALCULATOR_QUEUE = "TRAINER_CONTRIBUTION_CALCULATOR_QUEUE";

    Logger LOGGER = LoggerFactory.getLogger(TrainerContributionQueueSender.class);

    void sendMessage(TrainerWorkloadDto message);

    default boolean fallbackAcceptWorkload(TrainerWorkloadDto trainingWorkloadDto, Throwable throwable) {
        LOGGER.error("Error occurred while sending a message to: {}. The error is {}",
                TRAINER_CONTRIBUTION_CALCULATOR_QUEUE, throwable.getMessage(), throwable);

        return false;
    }
}
