package au.halc.kafka.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SettledTransaction {
	
	private BigDecimal amount;
	private String trn;
	private int fromAccount;
	private int toAccount;
	private int tranId;
	
	private LocalDateTime publishDateTime;
	private LocalDateTime settleDateTime;
	private LocalDateTime storeDateTime;
	

	public LocalDateTime getPublishDateTime() {
		return publishDateTime;
	}
	
	public LocalDateTime getSettleDateTime() {
		return settleDateTime;
	}
	
	public LocalDateTime getStoreDateTime() {
		return storeDateTime;
	}
	
	public void setStoreDateTime(LocalDateTime storeDateTimeP) {
		storeDateTime = storeDateTimeP;
	}
	
	public void setSettleDateTime(LocalDateTime settleDateTimeP) {
		settleDateTime = settleDateTimeP;
	}
	
	public void setPublishDateTime(LocalDateTime publishDateTimeP) {
		publishDateTime = publishDateTimeP;
	}
	
	
	
	public int getTranId() {
		return tranId;
	}

	public void setTranId(int tranIdP) {
		this.tranId = tranIdP;
	}

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
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("From Account :" + fromAccount).append(System.lineSeparator());
		stringBuilder.append("To Account :" + toAccount).append(System.lineSeparator());
		stringBuilder.append("Amount :" + amount).append(System.lineSeparator());
		stringBuilder.append("TRN :" + trn);
		stringBuilder.append("tran id :" + tranId);
		return stringBuilder.toString();
	}

}