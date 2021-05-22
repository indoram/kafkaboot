package au.halc.kafka.consumers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import au.halc.kafka.model.User;

import static au.halc.kafka.config.KafkaConstants.GROUP_ID;
import static au.halc.kafka.config.KafkaConstants.TOPIC;


@Service
public class KafkaConsumer {
	
	private final Logger logger 
			= LoggerFactory.getLogger(KafkaConsumer.class);
	
	@KafkaListener(topics = TOPIC, groupId = GROUP_ID)
	public void consume(String message) {
		logger.info(String.format("Message recieved -> %s", message));
	}

	@KafkaListener(topics = TOPIC, groupId = GROUP_ID)
	public void consume(User user) {
		logger.info(String.format("User created -> %s", user));
	}
}
