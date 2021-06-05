package au.halc.kafka.model;

import java.math.BigDecimal;

/**
 * Account Balance
 * @author indor
 */
public class AccountBalance {
	
	private BigDecimal amount;
	private int accountId;

	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amountP) {
		amount = amountP;
	}
	
	public int getAccountId() {
		return accountId;
	}
	
	public void setAccountId(int accountIdP) {
		accountId = accountIdP;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("amount :" + amount).append(System.lineSeparator());
		stringBuilder.append("account id :" + accountId).append(System.lineSeparator());
		return stringBuilder.toString();
	}
	
}