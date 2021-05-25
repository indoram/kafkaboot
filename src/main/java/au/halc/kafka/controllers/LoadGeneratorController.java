package au.halc.kafka.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import au.halc.kafka.config.KafkaAccountTransferConstants;
import au.halc.kafka.config.KafkaConstants;
import au.halc.kafka.model.AccountTransfer;
import au.halc.kafka.model.LoadGenerator;
import au.halc.kafka.services.AccountTransferService;

/**
 * Account transfer Load Generator
 * @author indor
 */
@Controller
@RequestMapping(path = "/kafka")
public class LoadGeneratorController {
	
	private final Logger logger 
			= LoggerFactory.getLogger(LoadGeneratorController.class);
	
	private static final String VIEW_NAME = "loadgenerator";
	private static final String LOAD_GEN_STR = "loadGenerator";
	
	private static final String ACCT_FROM_IDS = "fromAccountIds";
	
	private static final String DURATION_MILLIS_KEY = "durationKey";
	private static final String DURATION_MILLIS = "Time taken to publish ";
	private static final String MODEL_NAME = "acctbalances";
	

	@Autowired
    private AccountTransferService accountTransferService;
	
	@Autowired
    private KafkaTemplate<String, AccountTransfer> accountTransferKafkaTemplate;

	@GetMapping("/loadgenerator/setup")	
    public String setup(Model model, HttpServletRequest httpServletRequest) {
		model.addAttribute(LOAD_GEN_STR, createLoadGenerator());
		httpServletRequest.setAttribute(ACCT_FROM_IDS, KafkaAccountTransferConstants.getAccountIds());
		setCurrentBalances(httpServletRequest);
	    return VIEW_NAME;
    }
	
	@PostMapping(path = "/loadgenerator/generateload.form")
	public String generateLoad(@ModelAttribute LoadGenerator loadGenerator, Model model, HttpServletRequest httpServletRequest) {
		logger.info("Load Generator input {} ", loadGenerator.toString());
		logger.info("**** Starting to generate load ***");
		long startTime = System.currentTimeMillis();
		generateLoad(loadGenerator);
		long endTime = System.currentTimeMillis();
		long deltaMillis = endTime - startTime;
		logger.info("**** Ended generating load *** {} ms", deltaMillis);
		model.addAttribute(LOAD_GEN_STR, createLoadGenerator());
		
		String strValue =  DURATION_MILLIS + loadGenerator.getNoOfTransactions() + " messages took "  + deltaMillis + " (ms)"; 
		httpServletRequest.setAttribute(DURATION_MILLIS_KEY, strValue);
		httpServletRequest.setAttribute(ACCT_FROM_IDS, KafkaAccountTransferConstants.getAccountIds());
		
		waitForConsumerToCathUp();
		setCurrentBalances(httpServletRequest);
		return VIEW_NAME; 
	}
	
	
	private void waitForConsumerToCathUp() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			logger.error("waitForConsumerToCathUp", e);
		}
	}
	
	private void setCurrentBalances(HttpServletRequest httpServletRequest) {
		Map<Integer, BigDecimal> currBalances = accountTransferService.getAccountTransfers();
		httpServletRequest.setAttribute(MODEL_NAME, currBalances);
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