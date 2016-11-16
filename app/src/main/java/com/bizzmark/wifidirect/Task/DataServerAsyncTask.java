package com.bizzmark.wifidirect.Task;

/**
 * Created by Venu gopal on 24-10-2016.
 */

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.bizzmark.db.EarnBO;
import com.bizzmark.wifidirect.WifiDirectReceive;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

/**
 * A simple server socket that accepts connection and writes some data on
 * the stream.
 */
public class DataServerAsyncTask extends AsyncTask<Void, Void, String> {

    // private TextView statusText;
    // private WifiDirectReceive activity;

    private TextView statusText;
    private WifiDirectReceive activity;

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    DatabaseReference clientDatabase = database.child("client");

    /**
     * @param statusText
     */
    public DataServerAsyncTask(WifiDirectReceive activity, View statusText) {
        this.statusText = (TextView) statusText;
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {

            Log.i("bizzmark", "ServerSocket at 8888.");
            ServerSocket serverSocket = new ServerSocket(8888);

            Socket client = serverSocket.accept();
            Log.i("bizzmark", "Socket accepted.");
            InputStream inputstream = client.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int i;
            while ((i = inputstream.read()) != -1) {
                baos.write(i);
            }

            String str = baos.toString();
            serverSocket.close();
            return str;

        } catch (IOException e) {
            Log.e("bizzmark", e.toString());
            return null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    @Override
    protected void onPostExecute(String result) {

        Log.i("bizzmark", "onPostExecute.");

        Toast.makeText(activity, "result: " + result, Toast.LENGTH_SHORT).show();

        if (result != null) {
            statusText.setText("From customer: " + result);
        }

        Gson gson = new Gson();
        EarnBO earnBO = gson.fromJson(result, EarnBO.class);

        // EarnBO earnBO = new EarnBO("Venu", "Venu store", "2500", "2500");
        try {
            clientDatabase.setValue(earnBO);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see android.os.AsyncTask#onPreExecute()
     */
    @Override
    protected void onPreExecute() {

    }
}
