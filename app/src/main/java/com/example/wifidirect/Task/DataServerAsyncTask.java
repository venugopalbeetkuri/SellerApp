package com.example.wifidirect.Task;

/**
 * Created by Provigil on 24-10-2016.
 */

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.example.wifidirect.WifiDirectReceive;

/**
 * A simple server socket that accepts connection and writes some data on
 * the stream.
 */
public class DataServerAsyncTask extends AsyncTask<Void, Void, String> {

    private TextView statusText;
    private WifiDirectReceive activity;

    static ServerSocket serverSocket = null;

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

             if(null == serverSocket) {

                Log.i("bizzmark", "data doing back");
                serverSocket = new ServerSocket(8888);
                serverSocket.setReuseAddress(true);
             }


            Log.i("bizzmark", "Opening socket on 8888.");
            Socket client = serverSocket.accept();

            Log.i("bizzmark", "Client connected.");
            InputStream inputstream = client.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int i;
            while ((i = inputstream.read()) != -1) {
                baos.write(i);
            }

            String str = baos.toString();

           /* try{

                OutputStream outputstream = client.getOutputStream();
                outputstream.write("success\r\n".getBytes());
            }catch(Throwable th){
                th.printStackTrace();
            }*/



            return str;

        } catch (Throwable e) {

            Log.e("bizzmark", e.toString());
            try {
                serverSocket.close();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            serverSocket = null;
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

        Log.i("bizzmark", "data on post execute.Result: " + result);

        Toast.makeText(activity, "From customer: " + result, Toast.LENGTH_SHORT).show();

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
