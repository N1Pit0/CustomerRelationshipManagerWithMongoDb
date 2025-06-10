package com.mygym.crm.backstages.config;

import org.apache.activemq.ActiveMQConnectionFactory;
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

    private DiscoveryClient discoveryClient;

    @Autowired
    public void setDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        var converter = new MappingJackson2MessageConverter();

        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");

        return converter;
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

}
