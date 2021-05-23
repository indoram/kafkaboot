package au.halc.kafka.model;

public class LoadGenerator {	
	
	private int noOfTransactions;
	private int durationSecs;
	private long delayInMillis;

	public long getDelayInMillis() {
		return delayInMillis;
	}

	public void setDelayInMillis(long delayInMillis) {
		this.delayInMillis = delayInMillis;
	}

	public int getDurationSecs() {
		return durationSecs;
	}

	public void setDurationSecs(int durationSecs) {
		this.durationSecs = durationSecs;
	}

	public int getNoOfTransactions() {
		return noOfTransactions;
	}

	public void setNoOfTransactions(int noOfTransactions) {
		this.noOfTransactions = noOfTransactions;
	}
	

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("No of Transactions :" + noOfTransactions).append(System.lineSeparator());
		stringBuilder.append("Duration Secs :" + durationSecs).append(System.lineSeparator());
		stringBuilder.append("Delay Millis :" + delayInMillis).append(System.lineSeparator());
		return stringBuilder.toString();
	}

}
