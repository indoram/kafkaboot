package au.halc.kafka.consumer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import au.halc.kafka.config.KafkaConstants;
import au.halc.kafka.model.User;

@RestController
@RequestMapping(path = "/kafka")
public class UserResource {
	
	
	@Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

	@GetMapping("/publish/{name}")
    public String post(@PathVariable("name") final String name) {
		User user = new User(name, "Technology", 1200L);
		
		
        kafkaTemplate.send(KafkaConstants.TOPIC, new User(name, "Technology", 1200L));
        return "Published successfully";
    }
	
	
}
