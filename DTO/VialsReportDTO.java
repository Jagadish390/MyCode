package com.java.DTO;

public class VialsReportDTO {

	private String fromDate;
	private String toDate;

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	private String machineNumber;
	private String shift;

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

	public String getMachineNumber() {
		return machineNumber;
	}

	public void setMachineNumber(String machineNumber) {
		this.machineNumber = machineNumber;
	}

}
