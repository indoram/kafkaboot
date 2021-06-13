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
import au.halc.kafka.model.SettledTransaction;
import au.halc.kafka.dao.CommonDAO;
import au.halc.kafka.dao.AccountTransferDAO;


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
	private CommonDAO commonDAO;
	
	@Autowired
	private AccountTransferDAO accountTransferDAO;
	
	
	@Autowired
	private WebClient consumerWebClient;
	
	@Autowired
	private WebClient producerWebClient;
	
	@GetMapping("/web/dbtps/info.form")	
    public String setup(Model model, HttpServletRequest httpServletRequest) {
		
		List<DBTps> dbTpsList = commonDAO.fetchDBTpsForLastMinute();
		
		int totalNo = 0;
		long totalTimeMillis = 0L;
		
		for (DBTps dbTps : dbTpsList) {
			logger.info("dbTps {} {}", dbTps.getTotalNo(), dbTps.getTimeInMillis());
			totalNo += dbTps.getTotalNo();
			totalTimeMillis += dbTps.getTimeInMillis();
		}
		
		String dbTpsStr = "No Account Transfers recorded!";
		
		if (totalTimeMillis > 0 && totalNo > 0 ) {
			//int secs = (int) (totalTimeMillis / 1000);			
			//String tps = String.valueOf((totalNo / secs));			
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(totalNo).append(" transactions ").append(" took ").append( totalTimeMillis);
			stringBuilder.append(" (ms). ");
			//stringBuilder.append("TPS :").append(tps);
			dbTpsStr = stringBuilder.toString();
		}
		
		httpServletRequest.setAttribute(DB_TPS_KEY, dbTpsStr);
        return VIEW_NAME;
    } 
	
	@GetMapping("/web/dbbrowse/info.form")
    public String browseLast500(Model model, HttpServletRequest httpServletRequest) {		
		List<SettledTransaction> accountTransfersList = accountTransferDAO.fetchLast500SettledTransfers();
		httpServletRequest.setAttribute(VIEW_SETTLED_NAME, accountTransfersList);
        return VIEW_SETTLED_NAME;
    } 
}
