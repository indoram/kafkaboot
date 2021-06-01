package au.halc.kafka.producer.controllers;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.RestController;
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
	
	
	@GetMapping(path = "/producer/accounttransfers/publish.form")	
    public String GetPublish(Model model, HttpServletRequest httpServletRequest) {		
		AccountTransfer accountTransfer = createAccountTransfer();
		return publishSingleAccountTransfer(accountTransfer);
    }

	private void createNewAcctTransfer(Model model, HttpServletRequest httpServletRequest) {
		model.addAttribute(ACCT_MODEL_STR, createAccountTransfer());		
		model.addAttribute(ACCT_FROM_IDS, KafkaAccountTransferConstants.getAccountIds());		
		httpServletRequest.setAttribute(ACCT_FROM_IDS, KafkaAccountTransferConstants.getAccountIds());
		httpServletRequest.setAttribute(ACCT_TO_IDS, KafkaAccountTransferConstants.getToAccountIds());		
		model.addAttribute(ACCT_FROM_IDS, createAccountTransfer());
	}
	
	@PostMapping(path = "/producer/accounttransfers/publish.form")
	public String publish(@ModelAttribute AccountTransfer accountTransfer, Model model, HttpServletRequest httpServletRequest) {
		logger.info("Publishing {} ", accountTransfer.toString());
		return publishSingleAccountTransfer(accountTransfer); 
	}
	
	private String publishSingleAccountTransfer(AccountTransfer accountTransfer) {
		logger.info("Publishing {} ", accountTransfer.toString());
		accountTransferKafkaTemplate.send(KafkaConstants.TOPIC, accountTransfer);
		logger.info("Published successfully {} ", accountTransfer.toString());
        return "Published successfully";
	}
	
	private void waitForConsumerToCathUp() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			logger.error("waitForConsumerToCathUp", e);
		}
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
