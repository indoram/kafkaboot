package au.halc.kafka.config;

public final class KafkaConstants {

	public static final String GROUP_ID = "group-id";
	public static final String TOPIC = "Kafka_Example";
	public static final String SETTLED_TOPIC = "settled_topic";
	
	//dev
	public static final String BOOTSTRAP_ADDRESS = "pkc-epwny.eastus.azure.confluent.cloud:9092";
	//dedicated
	//public static final String BOOTSTRAP_ADDRESS = "pkc-pr90o.eastus.azure.confluent.cloud:9092";
	
	public static final int DB_BATCH = 1000;
	
	public static final String CONSUMER_ENDPOINT = "http://localhost:8083";
	
	public static final String PRODUCER_ENDPOINT = "http://localhost:8082";
	
	public static final int FLUX_BLOCK_TIME_SECS = 5;
}