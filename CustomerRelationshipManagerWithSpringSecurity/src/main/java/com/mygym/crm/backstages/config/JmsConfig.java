package com.mygym.crm.backstages.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@EnableJms

public class JmsConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsConfig.class);
    private DiscoveryClient discoveryClient;
    private ObjectMapper objectMapper;

    @Autowired
    public void setDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        var converter = new MappingJackson2MessageConverter();

        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        converter.setObjectMapper(objectMapper);

        return converter;
    }

    @CircuitBreaker(name = "trainerHoursCircuitBreaker", fallbackMethod = "fallbackAcceptWorkload")
    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        var factory = new ActiveMQConnectionFactory();

        var serviceInstance = discoveryClient.getInstances("standalone-activemq-wrapper").stream()
                .findFirst().orElseThrow();

        String brokerURL = serviceInstance.getMetadata().get("broker-url");
        String brokerPassword = serviceInstance.getMetadata().get("broker-password");
        String brokerUser = serviceInstance.getMetadata().get("broker-user");

        factory.setBrokerURL(brokerURL);
        factory.setUserName(brokerUser);
        factory.setPassword(brokerPassword);

        return factory;
    }

    private ActiveMQConnectionFactory fallbackAcceptWorkload(Throwable throwable) {
        LOGGER.error("Error occurred while calling acceptWorkload method: {}", throwable.getMessage(), throwable);

        LOGGER.warn("Do not use the default factory");
        return new ActiveMQConnectionFactory();
    }

}
