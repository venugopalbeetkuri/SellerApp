package com.bizzmark.sellerapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bizzmark.R;
import com.bizzmark.db.EarnBO;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.net.InetAddress;

public class EarnPoints extends AppCompatActivity {


    String userId,storeId,points, bill;

    final static String log = "Seller app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earn);

        Intent intent = getIntent();

        points = intent.getStringExtra("points");
        bill = intent.getStringExtra("billAmount");
        userId = intent.getStringExtra("userId");
        storeId = intent.getStringExtra("storeId");

        TextView redeemPoints = (TextView) findViewById(R.id.points);
        redeemPoints.setText(points);

        TextView billAmount = (TextView) findViewById(R.id.billAmountEarn);
        billAmount.setText(bill);

        addListenerOnAcceptButton();
        addListenerOnCancelButton();

    }

    /**
     * Earn Button click listener.
     *
     */
    public void addListenerOnCancelButton() {

        Button earnButton = (Button) findViewById(R.id.earnCancelButton);

        earnButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent itt = new Intent(EarnPoints.this, ReportPoints.class);
                itt.putExtra("storeId", storeId);
                startActivity(itt);
            }

        });

    }

    /**
     * Earn Button click listener.
     *
     */
    public void addListenerOnAcceptButton() {

        Button earnButton = (Button) findViewById(R.id.earnButton);

        earnButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                EarnBO earn = new EarnBO(userId, storeId, bill, points);

                // MongoDB db = new MongoDB();
                insertRecordEarn(earn);


            }

        });

    }

    public void insertRecordEarn(EarnBO earn) {

        //
        Log.i(log, "Earn points.");
        Document document = new Document();
        document.put("userId", earn.getUserId());
        document.put("storeId", earn.getStoreId());
        document.put("billAmount", earn.getBillAmount());
        document.put("earned", earn.getEarned());

        // collection.insertOne(document);
        insertDocument(document);
    }

    private void insertDocument(Document document) {

        try {
            // MongoCollection collection = getMongoCollection();
            AsyncTaskRunner task = new AsyncTaskRunner(document);
            task.execute();
            // String str_result = task.execute().get();
        } catch (Throwable th) {
            th.printStackTrace();
        }

        // String str_result= new RunInBackGround().execute().get();
    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        Document document = null;

        public AsyncTaskRunner(Document document) {
            this.document = document;
        }

        @Override
        protected String doInBackground(String... params) {

            // Calls onProgressUpdate()
            publishProgress("Sleeping...");
            try {

                if (isInternetAvailable()) {

                    Log.i(log, "Internet available.");
                }


                Log.i(log, "doInBackground");
                //MongoDB.getInstance().insertRecordEarn(d);

                MongoClientURI mongoURI = new MongoClientURI("mongodb://venugopalbeetkuri:shreshta143@ds015770.mlab.com:15770/pointshub");
                MongoClient mClient = new MongoClient(mongoURI);

                MongoDatabase db = mClient.getDatabase(mongoURI.getDatabase());

                MongoCollection collection = db.getCollection("client");

                //MongoCollection collection = getMongoCollection();

                collection.insertOne(document);

            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return "";
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String result) {
            Log.i("Seller app", "onPostExecute");
            // execution of result of Long time consuming operation
            // finalResult.setText(result);
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            Log.i("Seller app", "onPreExecute");
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onProgressUpdate(Progress[])
         */
        @Override
        protected void onProgressUpdate(String... text) {
            // finalResult.setText(text[0]);
            // Things to be done while execution of long running operation is in
            // progress. For example updating ProgessDialog

            Intent itt = new Intent(EarnPoints.this, ReportPoints.class);
            itt.putExtra("storeId", storeId);
            startActivity(itt);
        }
    }

    public boolean isInternetAvailable() {
        try {

            // You can replace it with your name.
            InetAddress ipAddr = InetAddress.getByName("google.com");

            if (ipAddr.equals("")) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            return false;
        }

    }

}
