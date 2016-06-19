package com.java.DTO;

public class PerformanceDTO {
	
	private String machineNumber;
	private int clockRate;
	private String articleId;
	private int runningTime;
	private String articleName;
	private int targetSpeed;
	
	public int getTargetSpeed() {
		return targetSpeed;
	}
	public void setTargetSpeed(int targetSpeed) {
		this.targetSpeed = targetSpeed;
	}
	public int getClockRate() {
		return clockRate;
	}
	public void setClockRate(int clockRate) {
		this.clockRate = clockRate;
	}
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public int getRunningTime() {
		return runningTime;
	}
	public void setRunningTime(int runningTime) {
		this.runningTime = runningTime;
	}
	public String getArticleName() {
		return articleName;
	}
	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}
	public String getMachineNumber() {
		return machineNumber;
	}
	public void setMachineNumber(String machineNumber) {
		this.machineNumber = machineNumber;
	}
	
	

}
