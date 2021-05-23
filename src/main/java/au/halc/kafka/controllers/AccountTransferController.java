package au.halc.kafka.controllers;

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

@Controller
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
	
	
	@GetMapping("/accounttransfers/setup")	
    public String setup(Model model, HttpServletRequest httpServletRequest) {
		createNewAcctTransfer(model, httpServletRequest);
        return VIEW_NAME;
    }

	private void createNewAcctTransfer(Model model, HttpServletRequest httpServletRequest) {
		model.addAttribute(ACCT_MODEL_STR, createAccountTransfer());		
		model.addAttribute(ACCT_FROM_IDS, KafkaAccountTransferConstants.getAccountIds());		
		httpServletRequest.setAttribute(ACCT_FROM_IDS, KafkaAccountTransferConstants.getAccountIds());
		httpServletRequest.setAttribute(ACCT_TO_IDS, KafkaAccountTransferConstants.getToAccountIds());		
		model.addAttribute(ACCT_FROM_IDS, createAccountTransfer());
	}
	
	@PostMapping(path = "/accounttransfers/transfer.form")
	public String publish(@ModelAttribute AccountTransfer accountTransfer, Model model, HttpServletRequest httpServletRequest) {
		logger.info("Publishing {} ", accountTransfer.toString());
		accountTransferKafkaTemplate.send(KafkaConstants.TOPIC, accountTransfer);
		logger.info("Published successfully {} ", accountTransfer.toString());
		httpServletRequest.setAttribute(TRANSFER_MSG_KEY, "Funds transferred successfully");
		
		createNewAcctTransfer(model, httpServletRequest);
		
		return VIEW_NAME; 
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
