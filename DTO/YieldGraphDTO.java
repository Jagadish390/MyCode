package com.java.DTO;

public class YieldGraphDTO {

	private String fromDate;
	private String toDate;
	private String shift;
	private String yield;
	private String machineNumber;
	private String yieldDate;
	
	
	public String getYieldDate() {
		return yieldDate;
	}
	public void setYieldDate(String yieldDate) {
		this.yieldDate = yieldDate;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getYield() {
		return yield;
	}
	public void setYield(String yield) {
		this.yield = yield;
	}
	public String getMachineNumber() {
		return machineNumber;
	}
	public void setMachineNumber(String machineNumber) {
		this.machineNumber = machineNumber;
	}
}
