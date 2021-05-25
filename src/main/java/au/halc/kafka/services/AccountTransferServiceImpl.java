package au.halc.kafka.services;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import au.halc.kafka.model.AccountTransfer;
import au.halc.kafka.repository.InMemoryAccountBalancesRepository;

/**
 * Account Transfer service Impl.
 * @author indor
 */
@Service
public class AccountTransferServiceImpl implements AccountTransferService {

	private final Logger logger 
		= LoggerFactory.getLogger(AccountTransferServiceImpl.class);
	
	private au.halc.kafka.repository.AccountBalancesRepository accountBalancesRepository;
	
	@Override
	public boolean accountTransfer(AccountTransfer accountTransfer) {
		logger.info("D Acc Id -> {} , C Acc Id -> {}", accountTransfer.getFromAccount(), 
				accountTransfer.getToAccount());
		
		if (accountBalancesRepository == null) {
			initAccountBalances();
		}
		
		return accountBalancesRepository.transfer(accountTransfer.getFromAccount(), accountTransfer.getToAccount(), 
				accountTransfer.getAmount());
	}


	@Override
	public void initAccountBalances() {
		accountBalancesRepository = new InMemoryAccountBalancesRepository();
		accountBalancesRepository.initCurrentBalances();
		logger.info("Account Balances initialised");
	}


	@Override
	public Map<Integer, BigDecimal> getAccountTransfers() {
		return accountBalancesRepository.getCurrentBalances();
	}

}
