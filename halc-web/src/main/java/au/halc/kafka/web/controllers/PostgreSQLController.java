package au.halc.kafka.web.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@GetMapping("/dbtps/info.form")	
    public String setup(Model model, HttpServletRequest httpServletRequest) {
		//httpServletRequest.setAttribute(DB_TPS_KEY, dbtps);
        return VIEW_NAME;
    } 
	
	@GetMapping("/dbbrowse/info.form")
    public String browseLast500(Model model, HttpServletRequest httpServletRequest) {
		//httpServletRequest.setAttribute(VIEW_SETTLED_NAME, accountTransfersList);
        return VIEW_SETTLED_NAME;
    } 
}
