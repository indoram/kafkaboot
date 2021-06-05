package au.halc.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.context.annotation.Import;

import au.halc.kafka.config.KafkaConstants;
import au.halc.kafka.config.KakfaConfiguration;

@Configuration
@Import({KakfaConfiguration.class})
public class KafkaWebConfiguration {
	
	
	@Bean
	public org.springframework.web.reactive.function.client.WebClient consumerWebClient() {
		return WebClient.create(KafkaConstants.CONSUMER_ENDPOINT);
	}
	
	@Bean
	public org.springframework.web.reactive.function.client.WebClient producerWebClient() {
		return WebClient.create(KafkaConstants.PRODUCER_ENDPOINT);
	}
	
}