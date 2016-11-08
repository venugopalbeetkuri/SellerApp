package com.bizzmark.wifidirect.Task;

/**
 * Created by Provigil on 24-10-2016.
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

import com.bizzmark.wifidirect.WifiDirectReceive;

/**
 * A simple server socket that accepts connection and writes some data on
 * the stream.
 */
public class DataServerAsyncTask extends AsyncTask<Void, Void, String> {

    // private TextView statusText;
    // private WifiDirectReceive activity;

    private TextView statusText;
    private WifiDirectReceive activity;

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
