package edu.school21.transportationcostsapplicationgenerator.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    Queue social_food() {
        return new Queue("transportation_costs", false);
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("SOCIAL_ASSISTANCE_EXCHANGE");
    }

    @Bean
    Binding social_food_binding() {
        return BindingBuilder.bind(social_food()).to(fanoutExchange());
    }

}
