package com.bizzmark.db;

/**
 * Created by Venu on 29-05-2016.
 */
public class ReportBO {

    String storeId;
    String pointsGiven;
    String totalSale;
    String totalDiscount;

    public  ReportBO(String storeId, String pointsGiven, String totalSale, String totalDiscount) {

        this.storeId = storeId;
        this.pointsGiven = pointsGiven;
        this.totalSale = totalSale;
        this.totalDiscount = totalDiscount;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getPointsGiven() {
        return pointsGiven;
    }

    public void setPointsGiven(String pointsGiven) {
        this.pointsGiven = pointsGiven;
    }

    public String getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(String totalSale) {
        this.totalSale = totalSale;
    }

    public String getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(String totalDiscount) {
        this.totalDiscount = totalDiscount;
    }
}
