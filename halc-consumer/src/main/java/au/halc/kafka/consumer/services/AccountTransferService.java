package au.halc.kafka.consumer.services;

import java.math.BigDecimal;
import java.util.List;
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
	
	List<AccountTransfer> fetchLast500Transfers();
	
}
