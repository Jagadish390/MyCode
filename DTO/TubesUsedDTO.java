package com.java.DTO;

public class TubesUsedDTO {

	private String productionDate;
	private String machineNumber;
	private String focusCode;
	private String tubeId;
	private String batchNumber;
	private String specification;
	private String tubesUsed;
	private String make;

	public String getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}
	public String getMachineNumber() {
		return machineNumber;
	}
	public void setMachineNumber(String machineNumber) {
		this.machineNumber = machineNumber;
	}
	public String getTubeId() {
		return tubeId;
	}
	public void setTubeId(String tubeId) {
		this.tubeId = tubeId;
	}

	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public String getTubesUsed() {
		return tubesUsed;
	}
	public void setTubesUsed(String tubesUsed) {
		this.tubesUsed = tubesUsed;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getFocusCode() {
		return focusCode;
	}
	public void setFocusCode(String focusCode) {
		this.focusCode = focusCode;
	}

}
