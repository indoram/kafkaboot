package au.halc.kafka.web.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.atomic.AtomicLong;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import au.halc.kafka.model.DBTps;
import au.halc.kafka.model.AccountTransfer;

/**
 * PostgreSQL Controller.
 * @author indor
 */
@Controller
@RequestMapping(path = "/kafka")
public class PostgreSQLController {

	private final Logger logger 
		= LoggerFactory.getLogger(PostgreSQLController.class);
	
	private static final String VIEW_NAME = "dbtps";
	
	private static final String VIEW_SETTLED_NAME = "settled";
	
	private static final String DB_TPS_KEY = "dbtps";
	
	@Autowired
	private WebClient consumerWebClient;
	
	@Autowired
	private WebClient producerWebClient;
	
	@GetMapping("/web/dbtps/info.form")	
    public String setup(Model model, HttpServletRequest httpServletRequest) {
		DBTps dbTps = new DBTps();
		
		DBTps dbResp = consumerWebClient.post()
		        .uri("/kafka/consumer/dbtps/info.form")
		        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		        .body(Mono.just(dbTps), DBTps.class)
		        .retrieve()
		        .bodyToMono(DBTps.class)
		        .block();
		
		AtomicLong timeInMillis = dbResp.getTimeInMillis();
		AtomicLong totalNo = dbResp.getTotalNo();
		
		String dbTpsStr = "No Account Transfers recorded!"; 
		
		if (timeInMillis.get() > 0 && totalNo.get() > 0 ) {
			String tps = String.valueOf((totalNo.get() / timeInMillis.get()));
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(totalNo.get()).append(" transactions ").append(" took ").append( timeInMillis.get());
			stringBuilder.append(" (ms). ");
			dbTpsStr = stringBuilder.toString();
		}
		
		httpServletRequest.setAttribute(DB_TPS_KEY, dbTpsStr);
        return VIEW_NAME;
    } 
	
	@GetMapping("/web/dbbrowse/info.form")
    public String browseLast500(Model model, HttpServletRequest httpServletRequest) {
		//httpServletRequest.setAttribute(VIEW_SETTLED_NAME, accountTransfersList);
        return VIEW_SETTLED_NAME;
    } 
}
