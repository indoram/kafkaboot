package au.halc.kafka.kafkaboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@ComponentScan({"au.halc.kafka.controllers", "au.halc.kafka.consumers", 
		"au.halc.kafka.config", "au.halc.kafka.services", 
		"au.halc.kafka.repository", "au.halc.kafka.dao"})
public class KafkabootApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkabootApplication.class, args);
	}

}
