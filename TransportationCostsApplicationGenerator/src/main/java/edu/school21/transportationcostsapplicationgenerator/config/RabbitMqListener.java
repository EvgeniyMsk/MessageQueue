package edu.school21.transportationcostsapplicationgenerator.config;

import com.google.gson.Gson;
import edu.school21.transportationcostsapplicationgenerator.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
public class RabbitMqListener {
    Logger logger = LoggerFactory.getLogger(RabbitMqListener.class);

    @RabbitListener(queues = "transportation_costs")
    public void processSocial_food(String message) {
        Person person = new Gson().fromJson(message, Person.class);
        logger.info("Received from transportation_costs: " + person.getAge());
    }
}
