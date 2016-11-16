package com.bizzmark.bo;

/**
 * Created by Venu gopal on 07-11-2016.
 */

public class Points {

    String userId;
    String storeId;
    String discountAmount;
    String redeemed;
    String earned;
    String billAmount;

    private Points(){

    }

    public Points(String userId,
                  String storeId,
                  String discountAmount,
                  String redeemed,
                  String earned,
                  String billAmount) {

        this.userId = userId;
        this.storeId = storeId;
        this.discountAmount = discountAmount;
        this.redeemed = redeemed;
        this.earned = earned;
        this.billAmount = billAmount;
    }

    public String getUserId() {
        return userId;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public String getRedeemed() {
        return redeemed;
    }

    public String getEarned() {
        return earned;
    }

    public String getBillAmount() {
        return billAmount;
    }
}
