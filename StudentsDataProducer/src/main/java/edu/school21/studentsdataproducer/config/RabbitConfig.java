package edu.school21.studentsdataproducer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    Logger logger = LoggerFactory.getLogger(RabbitConfig.class);

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("176.124.208.120", 5672);
        connectionFactory.setUsername("user");
        connectionFactory.setPassword("password");
        return connectionFactory;
    }

    @Bean
    AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }


    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("SOCIAL_ASSISTANCE_EXCHANGE");
    }

    @Bean
    Binding social_food_binding() {
        return BindingBuilder.bind(social_food()).to(fanoutExchange());
    }

    @Bean
    Binding financial_assistance_binding() {
        return BindingBuilder.bind(financial_assistance()).to(fanoutExchange());
    }

    @Bean
    Binding transportation_costs_binding() {
        return BindingBuilder.bind(transportation_costs()).to(fanoutExchange());
    }

    @Bean
    Queue social_food() {
        return new Queue("social_food", false);
    }

    @Bean
    Queue financial_assistance() {
        return new Queue("financial_assistance", false);
    }

    @Bean
    Queue transportation_costs() {
        return new Queue("transportation_costs", false);
    }

    @Bean
    TopicExchange topicExchange() { return new TopicExchange("GRANT_EXCHANGE"); }

    @Bean
    Binding grant_other_documents_binding() {
        return BindingBuilder.bind(grant_other_documents()).to(topicExchange()).with("grant.#");
    }

    @Bean
    Binding grant_contracts_binding() {
        return BindingBuilder.bind(grant_contracts()).to(topicExchange()).with("grant.1.*");
    }

    @Bean
    Queue grant_other_documents() {
        return new Queue("grant_other_documents", false);
    }

    @Bean
    Queue grant_contracts() {
        return new Queue("grant_contracts", false);
    }
}
