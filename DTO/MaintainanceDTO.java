package com.java.DTO;

import java.util.List;

public class MaintainanceDTO {

	private String id;
	private String batchNumber;
	private String maintenanceDate;

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
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

	public String getTimeLost() {
		return timeLost;
	}

	public void setTimeLost(String timeLost) {
		this.timeLost = timeLost;
	}

	public String getMaintenanceReason() {
		return maintenanceReason;
	}

	public void setMaintenanceReason(String maintenanceReason) {
		this.maintenanceReason = maintenanceReason;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getMaintenanceEngineer() {
		return maintenanceEngineer;
	}

	public void setMaintenanceEngineer(String maintenanceEngineer) {
		this.maintenanceEngineer = maintenanceEngineer;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMaintenanceDate() {
		return maintenanceDate;
	}

	public void setMaintenanceDate(String maintenanceDate) {
		this.maintenanceDate = maintenanceDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String shift;
	private String machineNumber;
	private String timeLost;
	private String maintenanceReason;
	private String detail;
	private String maintenanceEngineer;
	private String userId;
	private String type;
	private List<Integer> timeLosts;
	private List<String> maintenanceReasons;
	private List<String> details;

	
	
	public List<Integer> getTimeLosts() {
		return timeLosts;
	}

	public void setTimeLosts(List<Integer> timeLosts) {
		this.timeLosts = timeLosts;
	}

	public List<String> getMaintenanceReasons() {
		return maintenanceReasons;
	}

	public void setMaintenanceReasons(List<String> maintenanceReasons) {
		this.maintenanceReasons = maintenanceReasons;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
