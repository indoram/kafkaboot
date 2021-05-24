package au.halc.kafka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerializer;

import au.halc.kafka.model.AccountTransfer;
import au.halc.kafka.model.User;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

/**
 * Kafka configuration
 * @author indor
 */
@Configuration
public class KakfaConfiguration {

	
	public static final String PLAIN = "PLAIN";
	public static final String PROTOCOL_SSL = "SASL_SSL";
	public static final String PROTOCOL_SSL_PLAINTEXT = "SASL_PLAINTEXT";
	
	@Bean
    public ConsumerFactory<String, String> consumerFactory() {
		
        Map<String, Object> props = new HashMap<>();
        props.put(
          ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, 
          KafkaConstants.BOOTSTRAP_ADDRESS);
        props.put(
          ConsumerConfig.GROUP_ID_CONFIG, 
          KafkaConstants.GROUP_ID);
        props.put(
          ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, 
          StringDeserializer.class);
        props.put(
          ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, 
          org.springframework.kafka.support.serializer.JsonDeserializer.class);
        props.put("spring.kafka.consumer.properties.spring.json.trusted.packages", 
        		"au.halc.kafka.model");
        props.put("spring.json.trusted.packages", 
        		"au.halc.kafka.model");
         
        //props.put("security.protocol", "SASL_PLAINTEXT");
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, PROTOCOL_SSL);
        
        props.put(SaslConfigs.SASL_MECHANISM, PLAIN);
        
        props.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule   required username='N7I4DNGDGMWHQSAU'   password='e8VPxIUW5z8ea/FxNcGwTN+BnJWVBK9OJIiEpppQR/ECGingUqzppgsK2uZqPMF2';");
        
        return new DefaultKafkaConsumerFactory<>(props);
    }
	
	@Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> 
      kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
          new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
	
	@Bean
    public ProducerFactory<String, User> producerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.BOOTSTRAP_ADDRESS);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put( ProducerConfig.VALUE_SERIALIZER_CLASS_DOC, JsonSerializer.class);
         
        config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, PROTOCOL_SSL);
        
        config.put(SaslConfigs.SASL_MECHANISM, PLAIN);
        
        config.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule   required username='N7I4DNGDGMWHQSAU'   password='e8VPxIUW5z8ea/FxNcGwTN+BnJWVBK9OJIiEpppQR/ECGingUqzppgsK2uZqPMF2';");


        config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, PROTOCOL_SSL);
        
        config.put(SaslConfigs.SASL_MECHANISM, PLAIN);        
        config.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule   required username='N7I4DNGDGMWHQSAU'   password='e8VPxIUW5z8ea/FxNcGwTN+BnJWVBK9OJIiEpppQR/ECGingUqzppgsK2uZqPMF2';");
        
        return new DefaultKafkaProducerFactory<>(config);
    }


    @Bean
    public KafkaTemplate<String, User> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
    
    @Bean
    public ProducerFactory<String, AccountTransfer> accountTransferProducerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.BOOTSTRAP_ADDRESS);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);        
        config.put( ProducerConfig.VALUE_SERIALIZER_CLASS_DOC, JsonSerializer.class);
        
        config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, PROTOCOL_SSL);        
        config.put(SaslConfigs.SASL_MECHANISM, PLAIN);
        
        config.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule   required username='N7I4DNGDGMWHQSAU'   password='e8VPxIUW5z8ea/FxNcGwTN+BnJWVBK9OJIiEpppQR/ECGingUqzppgsK2uZqPMF2';");
        
        
        return new DefaultKafkaProducerFactory<>(config);
    }
    
    
    @Bean
    public KafkaTemplate<String, AccountTransfer> accountTransferKafkaTemplate() {
        return new KafkaTemplate<>(accountTransferProducerFactory());
    }
    
}
