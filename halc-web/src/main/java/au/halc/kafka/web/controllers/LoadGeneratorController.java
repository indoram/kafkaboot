package au.halc.kafka.web.controllers;

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

import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import au.halc.kafka.config.KafkaAccountTransferConstants;
import au.halc.kafka.config.KafkaConstants;
import au.halc.kafka.model.AccountTransfer;
import au.halc.kafka.model.LoadGenerator;
import au.halc.kafka.model.AccountBalance;

/**
 * Account transfer Load Generator
 * @author indor
 */
@Controller
@RequestMapping(path = "/kafka")
public class LoadGeneratorController {
	
	private final Logger logger 
			= LoggerFactory.getLogger(LoadGeneratorController.class);
	
	
	@Autowired
	private WebClient consumerWebClient;
	
	@Autowired
	private WebClient producerWebClient;
	
	private static final String VIEW_NAME = "loadgenerator";
	private static final String LOAD_GEN_STR = "loadGenerator";
	
	private static final String ACCT_FROM_IDS = "fromAccountIds";
	
	private static final String DURATION_MILLIS_KEY = "durationKey";
	private static final String DURATION_MILLIS = "Time taken to publish ";
	private static final String MODEL_NAME = "acctbalances";
	

	@GetMapping("/web/loadgenerator/setup.form")	
    public String setup(Model model, HttpServletRequest httpServletRequest) {
		model.addAttribute(LOAD_GEN_STR, createLoadGenerator());
		httpServletRequest.setAttribute(ACCT_FROM_IDS, KafkaAccountTransferConstants.getAccountIds());
		setCurrentBalances(httpServletRequest);
	    return VIEW_NAME;
    }
	
	@PostMapping(path = "/web/loadgenerator/generateload.form")
	public String generateLoad(@ModelAttribute LoadGenerator loadGenerator, Model model, HttpServletRequest httpServletRequest) {
		logger.info("Load Generator input {} ", loadGenerator.toString());
		LoadGenerator loadGeneration = submitLoad(loadGenerator);
		
		String strValue =  DURATION_MILLIS + loadGenerator.getNoOfTransactions() + " messages took "  + loadGeneration.getPublishTimeMillis() + " (ms)";
		
		httpServletRequest.setAttribute(DURATION_MILLIS_KEY, strValue);
		httpServletRequest.setAttribute(ACCT_FROM_IDS, KafkaAccountTransferConstants.getAccountIds());
		
		setCurrentBalances(httpServletRequest);
		return VIEW_NAME; 
	}
	
	private LoadGenerator submitLoad(LoadGenerator loadGenerator) {
		LoadGenerator loadGeneratorResp = producerWebClient.post()
		        .uri("/kafka/producer/loadgenerator/publish.form")
		        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		        .body(Mono.just(loadGenerator), LoadGenerator.class)
		        .retrieve()
		        .bodyToMono(LoadGenerator.class)
		        .block();
		return loadGeneratorResp;
	}
	
	private void setCurrentBalances(HttpServletRequest httpServletRequest) {
		List<AccountBalance> accoutBalancesList  = AccountBalancesRetriever.fetchAccountBalances(consumerWebClient);
		httpServletRequest.setAttribute(MODEL_NAME, accoutBalancesList);
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