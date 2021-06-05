package au.halc.kafka.consumer.controllers;

import java.math.BigDecimal;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import au.halc.kafka.consumer.services.AccountTransferService;

import au.halc.kafka.model.AccountBalance;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping(path = "/kafka")
public class AccountBalanceInitController {

	private final Logger logger 
				= LoggerFactory.getLogger(AccountBalanceInitController.class);
	
	private static final String VIEW_NAME = "acctbalances";
	
	private static final String MODEL_NAME = "acctbalances";
	
	@Autowired
	private AccountTransferService accountTransferService; 
	
	@GetMapping("/consumer/accountbalances/init.form")	
    public Flux<AccountBalance> setup(HttpServletRequest httpServletRequest) {
		logger.info("Consumer Initialising Account Balances.....");
		accountTransferService.initAccountBalances();		
		Map<Integer, BigDecimal> currBalances = accountTransferService.getCurrentBalances();		
		Map<Integer, AccountBalance> acctBals = convertToFlux(currBalances);
	    return Flux.fromIterable(acctBals.values());
    }
	
	
	private Map<Integer, AccountBalance> convertToFlux(Map<Integer, BigDecimal> currBalances) {
		Map<Integer, AccountBalance> acctBals = new HashMap<>();
		Set<Integer> keys = currBalances.keySet();
		
		for (int key : keys) {
			BigDecimal decimalValue = currBalances.get(key);
			AccountBalance acctBal = new AccountBalance();
			acctBal.setAmount(decimalValue);
			acctBal.setAccountId(key);
			acctBals.put(key, acctBal);
		}
		
		return acctBals;
	}
	
	 
	
	
	@GetMapping(path = "/consumer/accountbalances/fetch.form")	
    public Flux<AccountBalance> refresh(Model model, HttpServletRequest httpServletRequest) {
		logger.info("fetching Account Balances.....");
		Map<Integer, BigDecimal> currBalances = accountTransferService.getCurrentBalances();		
		Map<Integer, AccountBalance> acctBals = convertToFlux(currBalances);
	    return Flux.fromIterable(acctBals.values());
    }
	
	private void setCurrentBalances(HttpServletRequest httpServletRequest) {
		Map<Integer, BigDecimal> currBalances = accountTransferService.getCurrentBalances();
		httpServletRequest.setAttribute(MODEL_NAME, currBalances);
	}
	
}
