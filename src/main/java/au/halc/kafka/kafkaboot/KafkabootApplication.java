package au.halc.kafka.kafkaboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({" au.halc.kafka.controllers"})
public class KafkabootApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkabootApplication.class, args);
	}

}
