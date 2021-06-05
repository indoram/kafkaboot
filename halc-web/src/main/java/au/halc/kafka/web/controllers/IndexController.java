package au.halc.kafka.web.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for home page
 * @author indor
 */
@Controller
public class IndexController {

	private final Logger logger 
			= LoggerFactory.getLogger(IndexController.class);
	
	private static final String VIEW_NAME = "index";
	
	private static final String HYPER_LINKS = "halcLinks";
	
	@GetMapping("/")	
	 public String index(Model model, HttpServletRequest httpServletRequest) {
		logger.info("Index Page ....");
		httpServletRequest.setAttribute(HYPER_LINKS, getLinks());
	    return VIEW_NAME;
	 }
	
			
	private Map<String, String> getLinks() {
		Map<String, String> mapLinks = new LinkedHashMap<String, String>();
		mapLinks.put("kafka/web/accountbalances/init.form", "Reset Account Balances");
		mapLinks.put("kafka/web/accounttransfers/setup.form", "Single Account Transfer");
		mapLinks.put("kafka/web/accounttransfers/singletransfer.form", "Single Account Transfer");
		mapLinks.put("kafka/web/dbtps/info.form", "DB throughput (PostgreSQL)");
		mapLinks.put("kafka/web/dbbrowse/info.form", "Browse last 500 transactions (PostgreSQL)");
		return mapLinks;
	}
}
