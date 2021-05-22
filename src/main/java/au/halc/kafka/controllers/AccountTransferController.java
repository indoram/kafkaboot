package au.halc.kafka.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import au.halc.kafka.model.AccountTransfer;

@Controller
@RequestMapping(path = "/kafka")
public class AccountTransferController {
	
	@Autowired
    private KafkaTemplate<String, AccountTransfer> accountTransferKafkaTemplate;

	
	@GetMapping("/accounttransfers/setup")	
    public String setup() {
        return "accounttransfers";
    }
	
	
}
