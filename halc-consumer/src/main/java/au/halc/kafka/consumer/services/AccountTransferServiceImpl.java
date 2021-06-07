package au.halc.kafka.consumer.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.halc.kafka.dao.AccountTransferDAO;
import au.halc.kafka.model.AccountTransfer;
import au.halc.kafka.model.DBTps;
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
	
	
	private AtomicLong totalNo;
	private AtomicLong timeInMillis;
	
	
	@Autowired
	private AccountTransferDAO accountTransferDAO;
	
	@Override
	public DBTps getDBTPS() {
		DBTps dbTps = new DBTps();
		dbTps.setTotalNo(totalNo);
		dbTps.setTimeInMillis(timeInMillis);
		return dbTps;
	}
	
	
	@Override
	public boolean accountTransfer(AccountTransfer accountTransfer) {
		init();
		
		if (accountBalancesRepository == null) {
			initAccountBalances();
		}
		
		long t1 = System.currentTimeMillis();		
		accountTransferDAO.insert(accountTransfer);		
		long t2 = System.currentTimeMillis();
		
		long delta = (t2 - t1);
		
		logger.info("D Acc Id -> {} , C Acc Id -> {} millis {}", accountTransfer.getFromAccount(), 
				accountTransfer.getToAccount(), delta);
		
		
		totalNo.incrementAndGet();
		timeInMillis.addAndGet(delta);
		
		return accountBalancesRepository.transfer(accountTransfer.getFromAccount(), accountTransfer.getToAccount(), 
				accountTransfer.getAmount());
	}

	private void init() {
		if (totalNo == null) {
			totalNo = new AtomicLong();
		}
		
		if (timeInMillis == null) {
			timeInMillis = new AtomicLong();
		}
	}

	@Override
	public void initAccountBalances() {
		accountBalancesRepository = new InMemoryAccountBalancesRepository();
		accountBalancesRepository.initCurrentBalances();
		logger.info("Account Balances initialised");
	}


	@Override
	public Map<Integer, BigDecimal> getCurrentBalances() {
		return accountBalancesRepository.getCurrentBalances();
	}

	@Override
	public String getDBInsertTPS() {
		init();
		if (timeInMillis.get() > 0 && totalNo.get() > 0 ) {
			String tps = String.valueOf((totalNo.get() / timeInMillis.get()));
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(totalNo.get()).append(" transactions ").append(" took ").append( timeInMillis.get());
			stringBuilder.append(" (ms). ");
			return stringBuilder.toString();
		}
		return "No Account Transfers recorded!";
	}

	@Override
	public List<AccountTransfer> fetchLast500Transfers() {
		return accountTransferDAO.fetchLast500Transfers();
	}

}
