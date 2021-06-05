package au.halc.kafka.web.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.reactive.function.client.WebClient;

import au.halc.kafka.model.AccountBalance;
import au.halc.kafka.config.KafkaConstants;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class AccountBalancesRetriever {
	
	private static final Logger logger 
		= LoggerFactory.getLogger(AccountBalancesRetriever.class);
	
	public static List<AccountBalance> fetchAccountBalances(WebClient consumerWebClient) {
		Flux<AccountBalance> accountBalanceFlux = consumerWebClient.get()
				  .uri("/kafka/consumer/accountbalances/fetch.form")
				  .retrieve()
				  .bodyToFlux(AccountBalance.class);
		List<AccountBalance> accoutBalancesList  = accountBalanceFlux.collectList().block(java.time.Duration.ofSeconds(KafkaConstants.FLUX_BLOCK_TIME_SECS));
		logger.info("Account Balanaces {}", accoutBalancesList);
		return accoutBalancesList;
	}
	
	
	public static List<AccountBalance> resetAccountBalances(WebClient consumerWebClient) {
		Flux<AccountBalance> accountBalanceFlux = consumerWebClient.get()
				  .uri("/kafka/consumer/accountbalances/init.form")
				  .retrieve()
				  .bodyToFlux(AccountBalance.class);
		List<AccountBalance> accoutBalancesList  = accountBalanceFlux.collectList().block(java.time.Duration.ofSeconds(KafkaConstants.FLUX_BLOCK_TIME_SECS));
		logger.info("Account Balanaces {}", accoutBalancesList);
		return accoutBalancesList;
	}
}