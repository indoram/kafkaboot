package au.halc.kafka.model;

import java.util.concurrent.atomic.AtomicLong;

public class DBTps {
	
	private AtomicLong totalNo;
	private AtomicLong timeInMillis;
	
	public AtomicLong getTotalNo() {
		return totalNo;
	}
	
	public void setTotalNo(AtomicLong totalNoP) {
		totalNo = totalNoP;
	}
	
	public void setTimeInMillis(AtomicLong timeInMillisP) {
		timeInMillis = timeInMillisP;
	}
	
	public AtomicLong getTimeInMillis() {
		return timeInMillis;
	}
}