package com.java.DTO;

public class LookUpDTO {
private String param;
private String data;
private String userId;
private String maintainenceReasonId;
private String id;

public String getUserId() {
	return userId;
}
public void setUserId(String userId) {
	this.userId = userId;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getParam() {
	return param;
}
public void setParam(String param) {
	this.param = param;
}
public String getData() {
	return data;
}
public void setData(String data) {
	this.data = data;
}
public String getMaintainenceReasonId() {
	return maintainenceReasonId;
}
public void setMaintainenceReasonId(String maintainenceReasonId) {
	this.maintainenceReasonId = maintainenceReasonId;
}
}
