package com.bizzmark.db;

public class RedeemBO {
	
	String userId;
	String storeId;
	String billAmount;
	String redeemed;
	String discountAmount;

	public RedeemBO(String userId, String storeId, String billAmount, String redeemed, String discountAmount) {

		this.userId = userId;
		this.storeId = storeId;
		this.billAmount = billAmount;
		this.redeemed = redeemed;
		this.discountAmount = discountAmount;
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

	public String getRedeemed() {
		return redeemed;
	}

	public void setRedeemed(String redeemed) {
		this.redeemed = redeemed;
	}

	public String getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}

}
