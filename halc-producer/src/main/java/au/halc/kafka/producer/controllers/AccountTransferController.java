package au.halc.kafka.producer.controllers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
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

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * controller to receive requests to perform single account transfer
 * @author indor
 */
@RestController
@RequestMapping(path = "/kafka")
public class AccountTransferController {
	
	private final Logger logger 
			= LoggerFactory.getLogger(AccountTransferController.class);
	
	@Autowired
    private KafkaTemplate<String, AccountTransfer> accountTransferKafkaTemplate;
	

	private static final String ACCT_MODEL_STR = "accountTransfer";
	private static final String ACCT_FROM_IDS = "fromAccountIds";
	private static final String ACCT_TO_IDS = "toAccountIds";
	
	private static final String VIEW_NAME = "accounttransfers";
	
	private static final String TRANSFER_MSG_KEY = "transferSuccessMsg";
	
	private static final String MODEL_NAME = "acctbalances";
	
	@PostMapping(path = "/producer/singleaccounttransfer/transfer.form")
	public Mono<AccountTransfer> publish(@RequestBody AccountTransfer accountTransferMono, Model model, HttpServletRequest httpServletRequest) {
		logger.info("Producer Publishing {} ", accountTransferMono.toString());
		return Mono.just(publishSingleAccountTransfer(accountTransferMono));
	}
	
	private AccountTransfer publishSingleAccountTransfer(AccountTransfer accountTransfer) {
		accountTransfer.setPublishDateTime(LocalDateTime.now());
		accountTransferKafkaTemplate.send(KafkaConstants.TOPIC, accountTransfer);
		logger.info("Producer Published successfully {} ", accountTransfer.toString());
        return accountTransfer;
	}
	
	
}
