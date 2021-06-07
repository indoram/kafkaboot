package au.halc.kafka.producer.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import au.halc.kafka.config.KafkaAccountTransferConstants;
import au.halc.kafka.config.KafkaConstants;
import au.halc.kafka.model.AccountTransfer;
import au.halc.kafka.model.LoadGenerator;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Account transfer Load Generator
 * @author indor
 */
@RestController
@RequestMapping(path = "/kafka")
public class PublisherLoadGeneratorController {
	
	private final Logger logger 
			= LoggerFactory.getLogger(PublisherLoadGeneratorController.class);
	
	private static final String VIEW_NAME = "loadgenerator";
	private static final String LOAD_GEN_STR = "loadGenerator";
	
	private static final String ACCT_FROM_IDS = "fromAccountIds";
	
	private static final String DURATION_MILLIS_KEY = "durationKey";
	private static final String DURATION_MILLIS = "Time taken to publish ";
	private static final String MODEL_NAME = "acctbalances";
	

	@Autowired
    private KafkaTemplate<String, AccountTransfer> accountTransferKafkaTemplate;
	
	@PostMapping(path = "/producer/loadgenerator/publish.form")
	public Mono<LoadGenerator> generateLoad(@RequestBody LoadGenerator loadGenerator, HttpServletRequest httpServletRequest) {
		logger.info("Load Generator input {} ", loadGenerator.toString());
		logger.info("**** Starting to generate load ***");
		
		long startTime = System.currentTimeMillis();
		generateLoad(loadGenerator);
		long endTime = System.currentTimeMillis();
		
		long deltaMillis = endTime - startTime;
		logger.info("**** Ended generating load *** {} ms", deltaMillis);
		loadGenerator.setPublishTimeMillis(deltaMillis);
		return Mono.just(loadGenerator); 
	}
	
	
	
	private void generateLoad(LoadGenerator loadGenerator) {
		List<Integer> accountIds = KafkaAccountTransferConstants.getAccountIds();
		int maxSize = accountIds.size();
		Random rn = new Random();
		for (int i = 0; i < loadGenerator.getNoOfTransactions(); i++) {			
			int fromId = rn.nextInt(maxSize);
			int toId   = rn.nextInt(maxSize);			
			AccountTransfer acctTransfer = new AccountTransfer();
			acctTransfer.setFromAccount(accountIds.get(fromId));
			acctTransfer.setToAccount(accountIds.get(toId));
			acctTransfer.setTrn(buildAcctRef());
			acctTransfer.setAmount(KafkaAccountTransferConstants.TRAN_AMT);			
			accountTransferKafkaTemplate.send(KafkaConstants.TOPIC, acctTransfer);			
		}
	}
	
	private String buildAcctRef() {
		StringBuilder builder = new StringBuilder();
		builder.append(UUID.randomUUID().toString());
		return builder.toString();
	}
	
	private LoadGenerator createLoadGenerator() {
		LoadGenerator loadGenerator = new LoadGenerator();
		loadGenerator.setDurationSecs(KafkaAccountTransferConstants.DEFAULT_DURATION);
		loadGenerator.setNoOfTransactions(KafkaAccountTransferConstants.DEFAULT_MAX_OF_TRANSACTIONS);
		loadGenerator.setDelayInMillis(1);
		return loadGenerator;
	}
	
}