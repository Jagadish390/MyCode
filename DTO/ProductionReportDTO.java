package com.java.DTO;

import java.text.DecimalFormat;

import com.java.util.CommonRef;

public class ProductionReportDTO {
private String machineNumber;
private String usedKg;
private String goodKg;
private String novisProduced;
private String novisAccepted;
private String totalPacked;
private String timeLoss;
private String totalTime;





public ProductionReportDTO(String machineNumber, String usedKg, String goodKg,
		String novisProduced, String novisAccepted, String totalPacked,
		String timeLoss, String totalTime) {
	
	this.machineNumber = machineNumber;
	this.usedKg = usedKg;
	this.goodKg = goodKg;
	this.novisProduced = novisProduced;
	this.novisAccepted = novisAccepted;
	this.totalPacked = totalPacked;
	this.timeLoss = timeLoss;
	this.totalTime = totalTime;
}
public String getMachineNumber() {
	return machineNumber;
}
public void setMachineNumber(String machineNumber) {
	this.machineNumber = machineNumber;
}
public String getUsedKg() {
	return usedKg;
}
public void setUsedKg(String usedKg) {
	this.usedKg = usedKg;
}
public String getGoodKg() {
	return goodKg;
}
public void setGoodKg(String goodKg) {
	this.goodKg =goodKg;
}
public String getNovisProduced() {
	return novisProduced;
}
public void setNovisProduced(String novisProduced) {
	this.novisProduced = novisProduced;
}
public String getNovisAccepted() {
	return novisAccepted;
}
public void setNovisAccepted(String novisAccepted) {
	this.novisAccepted = novisAccepted;
}
public String getTotalPacked() {
	return totalPacked;
}
public void setTotalPacked(String totalPacked) {
	this.totalPacked = totalPacked;
}
public String getTimeLoss() {
	return timeLoss;
}
public void setTimeLoss(String timeLoss) {
	this.timeLoss = timeLoss;
}
public String getTotalTime() {
	return totalTime;
}
public void setTotalTime(String totalTime) {
	this.totalTime = totalTime;
}

private double novisYieldActual;
private double novisYieldTarget;
private double loadingActual;
private double loadingTarget;
private double utilizationActual;
private double utilizationTarget;
private double glassYieldActual;
private double glassYieldTarget;
private double performanceActual;
private double performanceTarget;
private double oeeActual;
private double oeeTarget;
private double oaeActual;
private double oaeTarget;

public double getNovisYieldActual() {
	return novisYieldActual;
}
public void setNovisYieldActual(double novisYieldActual) {
	DecimalFormat decimalFormat = new DecimalFormat("#.##");
	String a = decimalFormat.format(novisYieldActual);
	this.novisYieldActual = Double.parseDouble(a);
	
}
public double getNovisYieldTarget() {
	return novisYieldTarget;
}
public void setNovisYieldTarget(double novisYieldTarget) {
	
	this.novisYieldTarget = 98;
}
public double getLoadingActual() {
	return loadingActual;
}
public void setLoadingActual(double loadingActual) {
	this.loadingActual = loadingActual;
}
public double getLoadingTarget() {
	return loadingTarget;
}
public void setLoadingTarget(double loadingTarget) {
	this.loadingTarget = 100;
}
public double getUtilizationActual() {
	return utilizationActual;
}
public void setUtilizationActual(double utilizationActual) {
	DecimalFormat decimalFormat = new DecimalFormat("#.##");
	String a = decimalFormat.format(utilizationActual);
	this.utilizationActual = Double.parseDouble(a);

}
public double getUtilizationTarget() {
	return utilizationTarget;
}
public void setUtilizationTarget(double utilizationTarget) {
	this.utilizationTarget = 82;
}
public double getGlassYieldActual() {
	return glassYieldActual;
}
public void setGlassYieldActual(double glassYieldActual) {
	this.glassYieldActual =CommonRef.roundTwoDecimals( glassYieldActual);
}
public double getGlassYieldTarget() {
	return glassYieldTarget;
}
public void setGlassYieldTarget(double glassYieldTarget) {
	this.glassYieldTarget = 80;
}
public double getPerformanceActual() {
	return performanceActual;
}
public void setPerformanceActual(double performanceActual) {
	this.performanceActual = performanceActual;
}
public double getPerformanceTarget() {
	return performanceTarget;
}
public void setPerformanceTarget(double performanceTarget) {
	this.performanceTarget = 89;
}
public double getOeeActual() {
	return oeeActual;
}
public void setOeeActual(double oeeActual) {
/*	DecimalFormat decimalFormat = new DecimalFormat("#.##");
	String a = decimalFormat.format(oeeActual);*/
	//this.oeeActual = Double.parseDouble(a);
	this.oeeActual =oeeActual;
	
}
public double getOeeTarget() {
	return oeeTarget;
}
public void setOeeTarget(double oeeTarget) {
	this.oeeTarget = (getGlassYieldTarget()*getUtilizationTarget()*getPerformanceTarget())/10000;
}
public double getOaeActual() {
	
	
	return oaeActual;
}
public void setOaeActual(double oaeActual) {
		
	DecimalFormat decimalFormat = new DecimalFormat("#.##");
	String a = decimalFormat.format(oaeActual);
	this.oaeActual = Double.parseDouble(a);
	
}
public double getOaeTarget() {
	return oaeTarget;
}
public void setOaeTarget(double oaeTarget) {
	this.oaeTarget = (getGlassYieldTarget()*getUtilizationTarget()*getPerformanceTarget()*getLoadingTarget())/1000000;
}

}
