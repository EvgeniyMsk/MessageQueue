package edu.school21.studentsdataproducer.controller;

import com.google.gson.Gson;
import edu.school21.studentsdataproducer.entity.Person;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MainController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("person", new Person("Иванов", "Иван", 18, 2));
        return "form";
    }

    @PostMapping("/send")
    public String send(@ModelAttribute("person") Person person) {
        rabbitTemplate.setExchange("SOCIAL_ASSISTANCE_EXCHANGE");
        rabbitTemplate.convertAndSend(new Gson().toJson(person));
        rabbitTemplate.setExchange("GRANT_EXCHANGE");
        rabbitTemplate.convertAndSend(String.format("grant.%d.%s", person.getCourse(), "consent"), new Gson().toJson(person));
        rabbitTemplate.convertAndSend(String.format("grant.%d.%s", person.getCourse(), "grant"), new Gson().toJson(person));
        rabbitTemplate.convertAndSend(String.format("grant.%d.%s", person.getCourse(), "guarantee_letter"), new Gson().toJson(person));
        return "form";
    }
}
