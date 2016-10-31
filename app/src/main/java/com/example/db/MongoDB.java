package com.example.db;

import android.os.AsyncTask;
import android.util.Log;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
// import com.mongodb.MongoClientURI;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class MongoDB {

	static MongoDB mDB = null;

	public void printFirstRecord() {
		//
		//DBObject firstObject = pointsRows.findOne();

		//System.out.println(firstObject);
	}
	
	public ReportBO getReport(String storeId) {

		ReportBO report = null;
		try {

			Float billFloat = 0f;
			Float discountFloat = 0f;
			Float earnedPointsFloat = 0f;

			BasicDBObject query = new BasicDBObject("storeId", storeId);

			/*DBCursor cursor = pointsRows.find(query);

			while (cursor.hasNext()) {

				DBObject object = cursor.next();

				// Total Bill amount for total sale.
				Object billAmount = object.get("billAmount");
				if (null != billAmount) {

					String billStr = billAmount.toString();
					billFloat = billFloat + Float.parseFloat(billStr);
				}

				// Total Discount amount for discount amount.
				Object discountAmount = object.get("discountAmount");
				if (null != discountAmount) {

					String discountStr = discountAmount.toString();
					discountFloat = discountFloat + Float.parseFloat(discountStr);
				}

				// Total Points given for total bill.
				Object earnedPoints = object.get("earned");
				if (null != earnedPoints) {

					String earnedPointsStr = earnedPoints.toString();
					earnedPointsFloat = earnedPointsFloat + Float.parseFloat(earnedPointsStr);
				}

			}*/

			String totalSale = billFloat.toString();
			String totalDiscount = discountFloat.toString();
			String pointsGiven = earnedPointsFloat.toString();

			System.out.println("Bill amount: " + totalSale + " Discount amount: " + totalDiscount + " Total points given: " + pointsGiven);

			report = new ReportBO(storeId, pointsGiven, totalSale, totalDiscount);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return report;
	}

	public static void main(String[] args) {
		
		EarnBO earnBO = new EarnBO("venu123", "abc123", "1200", "300");

		/*MongoDB mongoDB = new MongoDB();
		mongoDB.insertRecordEarn(earnBO);*/
		
		
		/*RedeemBO redeem = new RedeemBO("khaizar456", "store-1", "2200", "200", "150");
		MongoDB.getInstance().insertRecordRedeem(redeem);*/
		
		// MongoDB.getInstance().printFirstRecord();


		//MongoDB.getInstance().getReport("xyz");
	}

}
