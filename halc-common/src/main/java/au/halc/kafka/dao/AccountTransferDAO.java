package au.halc.kafka.dao;

import java.util.List;

import au.halc.kafka.model.AccountTransfer;

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

	List<AccountTransfer> fetchLast500Transfers();
}
