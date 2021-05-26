package au.halc.kafka.services;

import java.math.BigDecimal;
import java.util.Map;

import au.halc.kafka.model.AccountTransfer;

/**
 * Account Transfer Service.
 * @author indor
 */
public interface AccountTransferService {
	
	boolean accountTransfer(AccountTransfer accountTransfer);
	
	void initAccountBalances();
	
	Map<Integer, BigDecimal> getAccountTransfers();
	
	String getDBInsertTPS();
	
}
