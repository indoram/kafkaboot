package au.halc.kafka.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import au.halc.kafka.config.KafkaAccountTransferConstants;

/**
 * In Memory Account Balances Repository.
 * @author indor
 *
 */
public class InMemoryAccountBalancesRepository implements AccountBalancesRepository {
	
	private Map<Integer, BigDecimal> currentBalances;
	
	public void initCurrentBalances() {
		currentBalances = new ConcurrentHashMap<Integer, BigDecimal>();
		List<Integer> accountTransferIds = KafkaAccountTransferConstants.getAccountIds();
		for (int accountId : accountTransferIds) {
			currentBalances.put(accountId, KafkaAccountTransferConstants.INIT_AMOUNT);
		}
	}
	
	public boolean transfer(int debitAcctId, int creditAccId, BigDecimal amtToTransfer) {
		initCurrBalancesMap();
		BigDecimal debitBalance = currentBalances.get(debitAcctId);
		if (debitBalance != null) {
			currentBalances.put(debitAcctId, debitBalance.subtract(amtToTransfer));
		}
		
		BigDecimal creditBalance = currentBalances.get(creditAccId);
		
		if (creditBalance != null) {
			currentBalances.put(creditAccId, creditBalance.add(amtToTransfer));
		}
		return true;
	}
	
	private void initCurrBalancesMap() {
		if (currentBalances == null) {
			currentBalances = new ConcurrentHashMap<Integer, BigDecimal>();
		}
	}

	@Override
	public Map<Integer, BigDecimal> getCurrentBalances() {
		initCurrBalancesMap();
		Set<Entry<Integer, BigDecimal>> entries = currentBalances.entrySet();
		return (HashMap<Integer, BigDecimal>) entries.stream()
		  .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}
}
