package au.halc.kafka.repository;

import java.math.BigDecimal;
import java.util.Map;

public interface AccountBalancesRepository {

	public void initCurrentBalances();
	
	boolean transfer(int debitAcctId, int creditAccId, BigDecimal amtToTransfer);
	
	Map<Integer, BigDecimal> getCurrentBalances();
}
