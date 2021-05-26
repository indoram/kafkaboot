package au.halc.kafka.consumers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import au.halc.kafka.model.AccountTransfer;
import au.halc.kafka.model.User;
import au.halc.kafka.services.AccountTransferService;

import static au.halc.kafka.config.KafkaConstants.GROUP_ID;
import static au.halc.kafka.config.KafkaConstants.TOPIC;


/**
 * Consumer of Kafka topic.
 * @author indor
 */
@Service
public class KafkaConsumer {
	
	private final Logger logger 
			= LoggerFactory.getLogger(KafkaConsumer.class);
	

	@Autowired
	private AccountTransferService accountTransferService;
	
	/*@KafkaListener(topics = TOPIC, groupId = GROUP_ID)
	public void consume(String message) {
		logger.info(String.format("Message recieved -> %s", message));
	}*/

	@KafkaListener(topics = TOPIC, groupId = GROUP_ID)
	public void consume(AccountTransfer accountTransfer) {
		logger.info("Consumer Received to transfer funds {}", accountTransfer.toString());
		accountTransferService.accountTransfer(accountTransfer);
	}
	
	/**
	 * @KafkaListener(
  			topicPartitions = @TopicPartition(topic = "topicName",
  				partitionOffsets = {
    				@PartitionOffset(partition = "0", initialOffset = "0"), 
    				@PartitionOffset(partition = "3", initialOffset = "0")}),
  						containerFactory = "partitionsKafkaListenerContainerFactory")
	 * @param user
	 */
	
	@KafkaListener(topics = TOPIC, groupId = GROUP_ID)
	public void consume(User user) {
		logger.info(String.format("User created -> %s", user));
	}
}
