package com.java.DTO;

public class ArticleMasterDTO {

	private String id;
	private String articleName;
	private String focusCode;
	private String drawingNumber;
	private String specification;
	private String weight;
	private String userId;
	private int productTargetSpeed;

	public int getProductTargetSpeed() {
		return productTargetSpeed;
	}

	public void setProductTargetSpeed(int productTargetSpeed) {
		this.productTargetSpeed = productTargetSpeed;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getFocusCode() {
		return focusCode;
	}

	public void setFocusCode(String focusCode) {
		this.focusCode = focusCode;
	}

	public String getDrawingNumber() {
		return drawingNumber;
	}

	public void setDrawingNumber(String drawingNumber) {
		this.drawingNumber = drawingNumber;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

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

}
