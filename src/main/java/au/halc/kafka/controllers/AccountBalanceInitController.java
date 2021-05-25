package au.halc.kafka.controllers;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import au.halc.kafka.services.AccountTransferService;

@Controller
@RequestMapping(path = "/kafka")
public class AccountBalanceInitController {

	private final Logger logger 
				= LoggerFactory.getLogger(AccountBalanceInitController.class);
	
	private static final String VIEW_NAME = "acctbalances";
	
	private static final String MODEL_NAME = "acctbalances";
	
	@Autowired
	private AccountTransferService accountTransferService; 
	
	@GetMapping("/accountbalances/init.form")	
    public String setup(Model model, HttpServletRequest httpServletRequest) {
		logger.info("Initialising Account Balances.....");
		accountTransferService.initAccountBalances();
		setCurrentBalances(httpServletRequest);
	    return VIEW_NAME;
    }
	
	
	@PostMapping(path = "/accountbalances/refresh.form")	
    public String refresh(Model model, HttpServletRequest httpServletRequest) {
		logger.info("Refereshing Account Balances.....");
		setCurrentBalances(httpServletRequest);
	    return VIEW_NAME;
    }
	
	private void setCurrentBalances(HttpServletRequest httpServletRequest) {
		Map<Integer, BigDecimal> currBalances = accountTransferService.getAccountTransfers();
		httpServletRequest.setAttribute(MODEL_NAME, currBalances);
	}
	
}
