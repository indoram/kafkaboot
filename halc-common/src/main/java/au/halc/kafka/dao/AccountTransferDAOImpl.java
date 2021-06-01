package au.halc.kafka.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import au.halc.kafka.model.AccountTransfer;

/**
 * Account Transfer DAO impl.
 * @author indor
 */
@Repository
public class AccountTransferDAOImpl implements AccountTransferDAO {

	private final Logger logger 
		= LoggerFactory.getLogger(AccountTransferDAOImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	private String settled_trans = "INSERT INTO kafka_settled_transactions " + "(debitAccountId, creditAccountId, amount, trn) VALUES (?, ?, ?, ?)";
	
	private String settled_sql_last500 = "SELECT * FROM kafka_settled_transactions ORDER BY tran_id DESC limit 500;";
	
	@Override
	public void insert(AccountTransfer accountTransfer) {
		int total = jdbcTemplate.update(settled_trans, new Object[] {
					accountTransfer.getFromAccount(), accountTransfer.getToAccount(), accountTransfer.getAmount(), 
					accountTransfer.getTrn()
				});
		
		if (total != 1) {
			logger.error("Error when inserting settled transfers {}", accountTransfer.getTrn());
		}
	}

	@Override
	public void inserBatch(List<AccountTransfer> batchAccountTransfers) {
		
		int[] records = jdbcTemplate.batchUpdate(settled_trans, new BatchPreparedStatementSetter() {			
			public void setValues(PreparedStatement ptst, int i) throws SQLException {
				AccountTransfer acctTransfer = batchAccountTransfers.get(i);
				ptst.setLong(1, acctTransfer.getFromAccount());
				ptst.setLong(2, acctTransfer.getToAccount());
				ptst.setBigDecimal(3, acctTransfer.getAmount());
				ptst.setString(4, acctTransfer.getTrn());
			}
			public int getBatchSize() {
				return batchAccountTransfers.size();
			}
		});
		
		if (records != null && records.length == batchAccountTransfers.size()) {
			logger.error("Error when inserting account transfers");
		}
		
	}

	@Override
	public List<AccountTransfer> fetchLast500Transfers() {
		List<AccountTransfer> acctList = new ArrayList<>();
		jdbcTemplate.query(settled_sql_last500, rs -> {
			
			AccountTransfer acctTransfer = new AccountTransfer();
			
			String trn = rs.getString("trn");
			int debitAcctId = rs.getInt("debitAccountId");
			int creditAcctId = rs.getInt("creditAccountId");
			BigDecimal amount = rs.getBigDecimal("amount");
			Integer tranId = rs.getInt("tran_id");
			
			acctTransfer.setTrn(trn);
			acctTransfer.setFromAccount(debitAcctId);
			acctTransfer.setToAccount(creditAcctId);
			acctTransfer.setAmount(amount);
			acctTransfer.setTranId(tranId);
			
			acctList.add(acctTransfer);
		});
		return acctList;
	}

}
