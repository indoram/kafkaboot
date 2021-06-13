package au.halc.kafka.model;

import java.time.LocalDateTime;

public class DBTps {
	
	private int totalNo;
	private long timeInMillis;
	private LocalDateTime recordedTime;
	
	
	public int getTotalNo() {
		return totalNo;
	}
	
	public void setTotalNo(int totalNoP) {
		totalNo = totalNoP;
	}
	
	public void setTimeInMillis(long timeInMillisP) {
		timeInMillis = timeInMillisP;
	}
	
	public long getTimeInMillis() {
		return timeInMillis;
	}
	
	public LocalDateTime getRecordedTime() {
		return recordedTime;
	}
	
	public void setRecordedTime(LocalDateTime recordedTimeP) {
		this.recordedTime = recordedTimeP;
	}
}