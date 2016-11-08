package com.bizzmark.db;

public class EarnBO {

	String userId;
	String earned;
	String storeId;
	String billAmount;

	public EarnBO(String userId, String storeId, String billAmount, String earned) {

		this.userId = userId;
		this.storeId = storeId;
		this.billAmount = billAmount;
		this.earned = earned;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(String billAmount) {
		this.billAmount = billAmount;
	}

	public String getEarned() {
		return earned;
	}

	public void setEarned(String earned) {
		this.earned = earned;
	}

}
