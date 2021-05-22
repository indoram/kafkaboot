package au.halc.kafka.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import au.halc.kafka.model.User;

@RestController
@RequestMapping(path = "/kafka")
public class UserResource {
	
	private static final String TOPIC = "Kafka_Example";
	
	@Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

	@GetMapping("/publish/{name}")
    public String post(@PathVariable("name") final String name) {
        kafkaTemplate.send(TOPIC, new User(name, "Technology", 1200L));
        return "Published successfully";
    }
	
	
}
