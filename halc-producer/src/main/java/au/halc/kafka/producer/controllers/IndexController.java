package au.halc.kafka.producer.controllers;

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
		mapLinks.put("kafka/producer/accounttransfers/publish.form", "publish single account transfer to topic");
		mapLinks.put("kafka/producer/loadgenerator/publish.form", "publish multiple account transfers to topic");
		return mapLinks;
	}
}
