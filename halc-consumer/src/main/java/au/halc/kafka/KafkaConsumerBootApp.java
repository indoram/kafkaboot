package au.halc.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@ComponentScan({"au.halc.kafka.consumer.controllers", "au.halc.kafka.consumer", 
		"au.halc.kafka.config", "au.halc.kafka.consumer.listener", 
		"au.halc.kafka.repository", "au.halc.kafka.dao", "au.halc.kafka.consumer.services"})
public class KafkaConsumerBootApp {
	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumerBootApp.class, args);
	}
}
