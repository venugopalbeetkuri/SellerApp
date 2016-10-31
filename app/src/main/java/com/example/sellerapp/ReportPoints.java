package com.example.sellerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.TextView;

import com.example.R;
import com.example.db.ReportBO;


public class ReportPoints extends AppCompatActivity {


    String pointsGivenStr, totalSaleStr, totalDiscountStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Intent intent = getIntent();
        String storeId = intent.getStringExtra("storeId");
        ReportBO report = null;
        try {

            // report = MongoDB.getInstance().getReport(storeId);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

        if (null == report) {
            report = new ReportBO(storeId, "0", "0", "0");
        }

        TextView pointsGiven = (TextView) findViewById(R.id.pointsGiven);
        pointsGiven.setText(report.getPointsGiven());

        TextView totalSale = (TextView) findViewById(R.id.totalSale);
        totalSale.setText(report.getTotalSale());

        TextView totalDiscount = (TextView) findViewById(R.id.totalDiscount);
        totalDiscount.setText(report.getTotalDiscount());


    }


}
