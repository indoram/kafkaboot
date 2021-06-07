package au.halc.kafka.consumer.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import au.halc.kafka.model.AccountTransfer;
import au.halc.kafka.consumer.services.AccountTransferService;

import au.halc.kafka.model.DBTps;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * PostgreSQL Controller.
 * @author indor
 */
@RestController
@RequestMapping(path = "/kafka")
public class PostgreSQLController {

	private final Logger logger 
		= LoggerFactory.getLogger(PostgreSQLController.class);
	
	private static final String VIEW_NAME = "dbtps";
	
	private static final String VIEW_SETTLED_NAME = "settled";
	
	private static final String DB_TPS_KEY = "dbtps";
	
	@Autowired
    private AccountTransferService accountTransferService;
	
	@PostMapping("/consumer/dbtps/info.form")
	public Mono<DBTps> setup(@RequestBody DBTps dbTps, HttpServletRequest httpServletRequest) {
		logger.info("*** im he consumer ***** {}", dbTps);
		DBTps dbtpsResp = accountTransferService.getDBTPS();
        return Mono.just(dbtpsResp);
    } 
	
	@GetMapping("/dbbrowse/info.form")
    public String browseLast500(Model model, HttpServletRequest httpServletRequest) {
		List<AccountTransfer> accountTransfersList = accountTransferService.fetchLast500Transfers();
		httpServletRequest.setAttribute(VIEW_SETTLED_NAME, accountTransfersList);
        return VIEW_SETTLED_NAME;
    } 
}
