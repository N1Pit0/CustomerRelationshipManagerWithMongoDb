package com.mygym.crm.backstages.core.services.communication;

import com.mygym.crm.sharedmodule.TrainerWorkloadDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FunctionalInterface
public interface SendToTrainerContributionCalculatorQueue {

    String TRAINER_CONTRIBUTION_CALCULATOR_QUEUE = "TRAINER_CONTRIBUTION_CALCULATOR_QUEUE";

    Logger LOGGER = LoggerFactory.getLogger(SendToTrainerContributionCalculatorQueue.class);

    @CircuitBreaker(name = "trainerHoursCircuitBreaker", fallbackMethod = "fallbackAcceptWorkload")
    void sendMessage(Object message);

    default boolean fallbackAcceptWorkload(TrainerWorkloadDto trainingWorkloadDto, Throwable throwable) {
        LOGGER.error("Error occurred while sending a message to: {}. The error is {}",
                TRAINER_CONTRIBUTION_CALCULATOR_QUEUE , throwable.getMessage(), throwable);

        return false;
    }
}
