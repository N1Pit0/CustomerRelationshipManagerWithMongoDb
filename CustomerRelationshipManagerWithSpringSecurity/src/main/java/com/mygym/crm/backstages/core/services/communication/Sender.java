package com.mygym.crm.backstages.core.services.communication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("Sender")
public class Sender {

    private final JmsTemplate jmsTemplate;

    @Autowired
    public Sender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Transactional
    public void sendMessage(String destination, Object message) {
        jmsTemplate.convertAndSend(destination, message);
    }
}
