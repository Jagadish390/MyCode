package com.java.DTO;

public class MaintenanceUpdateTimeLost {
private	String batchNumber;
private String maintenanceDate;
private	String shift;
private	String machineNumber;
private	String maxId;
public String getMaxId() {
	return maxId;
}
public void setMaxId(String maxId) {
	this.maxId = maxId;
}
private String batchStartTime;
private String shiftStartTime;
private String shiftEndTime;
public String getShiftStartTime() {
	return shiftStartTime;
}
public void setShiftStartTime(String shiftStartTime) {
	this.shiftStartTime = shiftStartTime;
}
public String getShiftEndTime() {
	return shiftEndTime;
}
public void setShiftEndTime(String shiftEndTime) {
	this.shiftEndTime = shiftEndTime;
}
public String getBatchStartTime() {
	return batchStartTime;
}
public void setBatchStartTime(String batchStartTime) {
	this.batchStartTime = batchStartTime;
}
public String getBatchNumber() {
	return batchNumber;
}
public void setBatchNumber(String batchNumber) {
	this.batchNumber = batchNumber;
}
public String getMaintenanceDate() {
	return maintenanceDate;
}
public void setMaintenanceDate(String maintenanceDate) {
	this.maintenanceDate = maintenanceDate;
}
public String getShift() {
	return shift;
}
public void setShift(String shift) {
	this.shift = shift;
}
public String getMachineNumber() {
	return machineNumber;
}
public void setMachineNumber(String machineNumber) {
	this.machineNumber = machineNumber;
}

}
