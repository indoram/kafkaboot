package au.halc.kafka.web.controllers;

import java.math.BigDecimal;
import java.util.Map;
import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.reactive.function.client.WebClient;

import au.halc.kafka.model.AccountBalance;
import au.halc.kafka.config.KafkaConstants;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping(path = "/kafka")
public class AccountBalanceInitController {

	private final Logger logger 
				= LoggerFactory.getLogger(AccountBalanceInitController.class);
	
	private static final String VIEW_NAME = "acctbalances";	
	private static final String MODEL_NAME = "acctbalances";
	
	@Autowired
	private WebClient consumerWebClient;
	
	
	@GetMapping("/web/accountbalances/init.form")	
    public String setup(Model model, HttpServletRequest httpServletRequest) {
		logger.info("Received Request to Initialise Account Balances");
		List<AccountBalance> accoutBalancesList  = AccountBalancesRetriever.resetAccountBalances(consumerWebClient);
		logger.info("Account Balanaces {}", accoutBalancesList);
		setBalances(httpServletRequest, accoutBalancesList);
	    return VIEW_NAME;
    }
	
	private void setBalances(HttpServletRequest httpServletRequest, List<AccountBalance> accoutBalancesList) {
		httpServletRequest.setAttribute(MODEL_NAME, accoutBalancesList);
	}
	
	
	@PostMapping(path = "/web/accountbalances/refresh.form")	
    public String refreshPost(Model model, HttpServletRequest httpServletRequest) {
		refresh(model, httpServletRequest);
		return VIEW_NAME;
    }
	
	@GetMapping(path = "/web/accountbalances/refresh.form")	
    public String refresh(Model model, HttpServletRequest httpServletRequest) {
		logger.info("Refereshing Account Balances.....");		
		List<AccountBalance> accoutBalancesList  = AccountBalancesRetriever.resetAccountBalances(consumerWebClient);
		logger.info("Account Balanaces {}", accoutBalancesList);		
		setBalances(httpServletRequest, accoutBalancesList);
	    return VIEW_NAME;
    }
	
}
