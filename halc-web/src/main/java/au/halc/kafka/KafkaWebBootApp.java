package au.halc.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@SpringBootApplication
@EnableKafka
@ComponentScan({"au.halc.kafka.web.controllers", "au.halc.kafka", "au.halc.kafka.web",
		"au.halc.kafka.KafkaWebConfiguration",
		"au.halc.kafka.config", "au.halc.kafka.repository", "au.halc.kafka.dao", 
		"au.halc.kafka.web.services"})
@EnableAutoConfiguration
public class KafkaWebBootApp {
	public static void main(String[] args) {
		SpringApplication.run(KafkaWebBootApp.class, args);
	}
}
