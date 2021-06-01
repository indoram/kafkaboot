package au.halc.kafka.consumer.controllers;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import au.halc.kafka.config.KafkaConstants;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * Controller to create a new topic
 * @author indor
 */
@Controller
@RequestMapping(path = "/kafka")
public class CreateNewTopicController {

	public static final String PLAIN = "PLAIN";
	public static final String PROTOCOL_SSL = "SASL_SSL";
	public static final String PROTOCOL_SSL_PLAINTEXT = "SASL_PLAINTEXT";

	
	private final Logger logger 
			= LoggerFactory.getLogger(CreateNewTopicController.class);
	
	private static final String VIEW_NAME = "newtopic";
	
	private static final String NEW_TOPIC_NAME = "newtopic"; 
	
	
	
	@GetMapping("/newtopic/setup.form")	
    public String setup(Model model, HttpServletRequest httpServletRequest) {
		createNewTopic(model, httpServletRequest);
        return VIEW_NAME;
    }
	
	private void createNewTopic(Model model, HttpServletRequest httpServletRequest) {
		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.BOOTSTRAP_ADDRESS);
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);        
		properties.put( ProducerConfig.VALUE_SERIALIZER_CLASS_DOC, JsonSerializer.class);
        
		properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, PROTOCOL_SSL);        
		properties.put(SaslConfigs.SASL_MECHANISM, PLAIN);
        //dev
        properties.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule   required username='N7I4DNGDGMWHQSAU'   password='e8VPxIUW5z8ea/FxNcGwTN+BnJWVBK9OJIiEpppQR/ECGingUqzppgsK2uZqPMF2';");
        
	}
	
}
