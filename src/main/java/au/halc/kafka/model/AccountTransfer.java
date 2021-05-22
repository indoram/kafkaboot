package au.halc.kafka.model;

import java.math.BigDecimal;

public class AccountTransfer {

	private BigDecimal amount;
	private String trn;
	private int fromAccount;
	private int toAccount;
	

	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public String getTrn() {
		return trn;
	}
	
	public void setTrn(String trn) {
		this.trn = trn;
	}
	
	public int getFromAccount() {
		return fromAccount;
	}
	
	public void setFromAccount(int fromAccount) {
		this.fromAccount = fromAccount;
	}
	
	public int getToAccount() {
		return toAccount;
	}
	
	public void setToAccount(int toAccount) {
		this.toAccount = toAccount;
	}
	
}
