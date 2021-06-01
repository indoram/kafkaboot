package au.halc.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@ComponentScan({"au.halc.kafka.producer.controllers", "au.halc.kafka.producer", 
		"au.halc.kafka.config",  "au.halc.kafka.repository", 
		"au.halc.kafka.dao"})
public class KafkaProducerBootApp {
	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerBootApp.class, args);
	}
}
