package au.halc.kafka.web.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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
import au.halc.kafka.model.AccountBalance;

@Controller
@RequestMapping(path = "/kafka")
public class AccountTransferController {
	
	private final Logger logger 
			= LoggerFactory.getLogger(AccountTransferController.class);
	
	

	private static final String ACCT_MODEL_STR = "accountTransfer";
	private static final String ACCT_FROM_IDS = "fromAccountIds";
	private static final String ACCT_TO_IDS = "toAccountIds";
	
	private static final String VIEW_NAME = "accounttransfers";
	
	private static final String TRANSFER_MSG_KEY = "transferSuccessMsg";
	
	private static final String MODEL_NAME = "acctbalances";
	
	@Autowired
	private WebClient consumerWebClient;
	
	@Autowired
	private WebClient producerWebClient;
	
	
	@GetMapping("/web/accounttransfers/setup.form")	
    public String setup(Model model, HttpServletRequest httpServletRequest) {
		createNewAcctTransfer(model, httpServletRequest);
		setCurrentBalances(httpServletRequest);
        return VIEW_NAME;
    }

	private void createNewAcctTransfer(Model model, HttpServletRequest httpServletRequest) {
		model.addAttribute(ACCT_MODEL_STR, createAccountTransfer());		
		model.addAttribute(ACCT_FROM_IDS, KafkaAccountTransferConstants.getAccountIds());		
		httpServletRequest.setAttribute(ACCT_FROM_IDS, KafkaAccountTransferConstants.getAccountIds());
		httpServletRequest.setAttribute(ACCT_TO_IDS, KafkaAccountTransferConstants.getToAccountIds());		
		model.addAttribute(ACCT_FROM_IDS, createAccountTransfer());
	}
	
	@PostMapping(path = "/web/accounttransfers/transfer.form")
	public String publish(@ModelAttribute AccountTransfer accountTransfer, Model model, HttpServletRequest httpServletRequest) {
		logger.info("Publishing****** {} ", accountTransfer.toString());
		
		
		AccountTransfer publishedTransfer = producerWebClient.post()
		        .uri("/kafka/producer/singleaccounttransfer/transfer.form")
		        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		        .body(Mono.just(accountTransfer), AccountTransfer.class)
		        .retrieve()
		        .bodyToMono(AccountTransfer.class)
		        .block();
		
		logger.info("Published successfully {} ", publishedTransfer.toString());
		httpServletRequest.setAttribute(TRANSFER_MSG_KEY, "Funds transferred successfully");
		setCurrentBalances(httpServletRequest);
		createNewAcctTransfer(model, httpServletRequest);
		
		
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
		List<AccountBalance> accoutBalancesList  = AccountBalancesRetriever.fetchAccountBalances(consumerWebClient);
		httpServletRequest.setAttribute(MODEL_NAME, accoutBalancesList);
	}
	
	private AccountTransfer createAccountTransfer() {
		AccountTransfer acctTransfer = new AccountTransfer();
		acctTransfer.setFromAccount(KafkaAccountTransferConstants.ACCOUNT_ID_1);
		acctTransfer.setToAccount(KafkaAccountTransferConstants.ACCOUNT_ID_2);
		acctTransfer.setTrn(buildAcctRef());
		acctTransfer.setAmount(KafkaAccountTransferConstants.TRAN_AMT);
		return acctTransfer;				
	}
	
	private String buildAcctRef() {
		StringBuilder builder = new StringBuilder();
		builder.append(UUID.randomUUID().toString());
		return builder.toString();
	}
	
}
