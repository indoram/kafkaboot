package au.halc.kafka.web.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.kafka.common.PartitionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import au.halc.kafka.config.KafkaConstants;
import au.halc.kafka.model.AccountTransfer;

/**
 * Partition Info Controller
 * @author indor
 */
@Controller
@RequestMapping(path = "/kafka")
public class PartitionInfoController {

	private static final String VIEW_NAME = "partitioninfo";
	
	@Autowired
    private KafkaTemplate<String, AccountTransfer> accountTransferKafkaTemplate;
	
	
	private final Logger logger 
			= LoggerFactory.getLogger(PartitionInfoController.class);

	
	@GetMapping("/partition/info.form")	
    public String setup(Model model, HttpServletRequest httpServletRequest) {
		logger.info("Getting Partition Info....");
		java.util.List<org.apache.kafka.common.PartitionInfo> partitionList  = 
				accountTransferKafkaTemplate.partitionsFor(KafkaConstants.TOPIC);
		logger.info("Partition size {}", partitionList.size());
		for (PartitionInfo partitionInfo : partitionList) {
			logger.info("Partition  {} {}", 
					partitionInfo.partition(), partitionInfo.leader().idString());
		}
	    return VIEW_NAME;
    }
}
