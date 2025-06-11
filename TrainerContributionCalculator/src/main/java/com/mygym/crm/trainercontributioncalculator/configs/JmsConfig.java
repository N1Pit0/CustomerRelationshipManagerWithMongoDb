package com.mygym.crm.trainercontributioncalculator.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJms
@EnableTransactionManagement
public class JmsConfig {

    private DiscoveryClient discoveryClient;
    private ObjectMapper objectMapper;

    private static Logger LOGGER = LoggerFactory.getLogger(JmsConfig.class);

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

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

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        var converter = new MappingJackson2MessageConverter();

        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        converter.setObjectMapper(objectMapper);

        return converter;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        var factory = new DefaultJmsListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrency("1-5");
        factory.setMessageConverter(jacksonJmsMessageConverter());
        factory.setTransactionManager(transactionManager());
        factory.setErrorHandler(t -> {
            LOGGER.info("Handling error message {}", t.getMessage(), t);
        });

        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JmsTransactionManager(connectionFactory());
    }

    @Bean
    public JmsTemplate jmsTemplate(){
        var jmsTemplate = new JmsTemplate(connectionFactory());

        jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());
        jmsTemplate.setDeliveryPersistent(true);
        jmsTemplate.setSessionTransacted(true);

        return jmsTemplate;
    }
}
