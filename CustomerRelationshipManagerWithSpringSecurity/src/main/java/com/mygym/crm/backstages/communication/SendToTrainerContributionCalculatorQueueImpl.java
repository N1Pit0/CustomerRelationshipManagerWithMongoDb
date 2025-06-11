package com.mygym.crm.backstages.communication;

import com.mygym.crm.backstages.core.services.communication.SendToTrainerContributionCalculatorQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SendToTrainerContributionCalculatorQueueImpl implements SendToTrainerContributionCalculatorQueue {

    private final JmsTemplate jmsTemplate;

    @Autowired
    public SendToTrainerContributionCalculatorQueueImpl(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    @Transactional
    public void sendMessage(Object message) {
        jmsTemplate.convertAndSend(TRAINER_CONTRIBUTION_CALCULATOR_QUEUE, message);
    }
}

