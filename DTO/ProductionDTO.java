package com.java.DTO;

public class ProductionDTO {
	private String id;
	private String batchNumber;
	private String productionDate;
	private String shift;
	private String novisProd;
	private String novisGood;
	private String novisRotRej;
	private String novisLienRej;
	private String tubesUsed;
	private String vialsPerTube;
	private String timeLost;
	private String productionReason;
	private String reasonDetail;
	private String type;
	private String clockRate;
	private String glassWaste;
	private String activityTime;
	private String engineerPrd;
	private String userId;
	private String productionMaster;


	public String getClockRate() {
		return clockRate;
	}

	public void setClockRate(String clockRate) {
		this.clockRate = clockRate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ProductionDTO(String productionDate, String shift,
			String batchNumber, String novisProd, String novisGood,
			String novisRotRej, String novisLienRej, String tubesUsed,
			String timeLost, String productionReason, String reasonDetail,
			String glassWaste, String activityTime, String engineerPrd,
			String userId) {
		this.batchNumber = batchNumber;
		this.productionDate = productionDate;
		this.shift = shift;
		this.novisProd = novisProd;
		this.novisGood = novisGood;
		this.novisRotRej = novisRotRej;
		this.novisLienRej = novisLienRej;
		this.tubesUsed = tubesUsed;
		this.timeLost = timeLost;
		this.productionReason = productionReason;
		this.reasonDetail = reasonDetail;
		this.glassWaste = glassWaste;
		this.activityTime = activityTime;
		this.engineerPrd = engineerPrd;
		this.userId = userId;
	}

	public ProductionDTO() {
		// TODO Auto-generated constructor stub
	}

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

	public String getNovisProd() {
		return novisProd;
	}

	public void setNovisProd(String novisProd) {
		this.novisProd = novisProd;
	}

	public String getNovisGood() {
		return novisGood;
	}

	public void setNovisGood(String novisGood) {
		this.novisGood = novisGood;
	}

	public String getNovisRotRej() {
		return novisRotRej;
	}

	public void setNovisRotRej(String novisRotRej) {
		this.novisRotRej = novisRotRej;
	}

	public String getNovisLienRej() {
		return novisLienRej;
	}

	public void setNovisLienRej(String novisLienRej) {
		this.novisLienRej = novisLienRej;
	}

	public String getTubesUsed() {
		return tubesUsed;
	}

	public void setTubesUsed(String tubesUsed) {
		this.tubesUsed = tubesUsed;
	}

	public String getTimeLost() {
		return timeLost;
	}

	public void setTimeLost(String timeLost) {
		this.timeLost = timeLost;
	}

	public String getProductionReason() {
		return productionReason;
	}

	public void setProductionReason(String productionReason) {
		this.productionReason = productionReason;
	}

	public String getReasonDetail() {
		return reasonDetail;
	}

	public void setReasonDetail(String reasonDetail) {
		this.reasonDetail = reasonDetail;
	}

	public String getGlassWaste() {
		return glassWaste;
	}

	public void setGlassWaste(String glassWaste) {
		this.glassWaste = glassWaste;
	}

	public String getActivityTime() {
		return activityTime;
	}

	public void setActivityTime(String activityTime) {
		this.activityTime = activityTime;
	}

	public String getEngineerPrd() {
		return engineerPrd;
	}

	public void setEngineerPrd(String engineerPrd) {
		this.engineerPrd = engineerPrd;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVialsPerTube() {
		return vialsPerTube;
	}

	public void setVialsPerTube(String vialsPerTube) {
		this.vialsPerTube = vialsPerTube;
	}



	public String getProductionMaster() {
		return productionMaster;
	}

	public void setProductionMaster(String productionMaster) {
		this.productionMaster = productionMaster;
	}

}
