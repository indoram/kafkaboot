package au.halc.kafka.dao;

import java.util.List;

import au.halc.kafka.model.AccountTransfer;
import au.halc.kafka.model.SettledTransaction;

/**
 * Account Transfer DAO.
 * @author indor
 */
public interface AccountTransferDAO {
	
	/**
	 * inserts single account transfer
	 * @param accountTransfer the account transfer
	 */
	void insert(AccountTransfer accountTransfer);
	
	/**
	 * inserts batched account transfers
	 * @param batchAccountTransfers the batchAccountTransfers
	 */
	void inserBatch(List<AccountTransfer> batchAccountTransfers);
	
	//void inserBatch(List<SettledTransaction> settledTransactions);

	List<AccountTransfer> fetchLast500Transfers();
}
