package com.bizzmark.sellerapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;

import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

// import com.bizzmark.sellerapp.MainActivity.R;

import com.bizzmark.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 3;

    public static final int MESSAGE_READ = 2;

    Button earnButton = null;
    Button redeemButton = null;

    // Name for the SDP record when creating server socket
    private static final String NAME_SECURE = "BluetoothChatSecure";
    private static final String NAME_INSECURE = "BluetoothChatInsecure";

    // Unique UUID for this application
    private static final UUID MY_UUID_SECURE = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    private BluetoothAdapter mAdapter = null;

    ConnectedThread connectedThread = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            AcceptThread serverBluetoothAdapter = new AcceptThread();
            serverBluetoothAdapter.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        earnButton = (Button) findViewById(R.id.buttonEarn);

        // String userId = "Khaizar";
        // String points = "500";
        // String billAmount = "1000";
        // String discount = "250";
        // String storeId = "xyz";

        // EarnBO earn = new EarnBO(userId, storeId, billAmount, points);
        //MongoDB.getInstance();

        earnButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // sendNotification(storeId, "Earn", userId, points, billAmount, discount);
            }

        });

        redeemButton = (Button) findViewById(R.id.buttonRedeem);

        redeemButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // sendNotification(storeId, "Redeem", userId, points, billAmount, discount);
            }

        });

    }

    private void sendNotification(String storeId, String type, String userId, String points, String billAmount, String discount) {

        // Create Notification Builder.
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        // Setting Notification Properties.
        mBuilder.setSmallIcon(R.drawable.bell);

        // mBuilder.setContentTitle("Notification Alert, Click Me!");
        // mBuilder.setContentText("Hi, This is Android Notification Detail!");

        if("Earn".equalsIgnoreCase(type)) {

            mBuilder.setContentTitle(userId + " : " + "would like to earn points from your store.");
            mBuilder.setContentText("Earn " + points + " request. For bill amount: " + billAmount);
        } else if("Redeem".equalsIgnoreCase(type)) {

            mBuilder.setContentTitle(userId + " : " + "would like to redeem points from your store.");
            mBuilder.setContentText("Redeem " + points + " request. For bill amount: " + billAmount);
        }

        // Cancel the notification after its selected
        mBuilder.setAutoCancel(true);

        // Attach Actions.
        if ("Redeem".equalsIgnoreCase(type)) {

            Intent redeemIntent =  new Intent(this, RedeemPoints.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(RedeemPoints.class);
            redeemIntent.putExtra("points", points);
            redeemIntent.putExtra("billAmount", billAmount);
            redeemIntent.putExtra("discount", discount);
            redeemIntent.putExtra("userId", userId);
            redeemIntent.putExtra("storeId", storeId);

            // Adds the Intent that starts the Activity to the top of the stack.
            stackBuilder.addNextIntent(redeemIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);

        } else if ("Earn".equalsIgnoreCase(type)) {

            Intent earnIntent =  new Intent(this, EarnPoints.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(EarnPoints.class);
            earnIntent.putExtra("points", points);
            earnIntent.putExtra("billAmount", billAmount);
            earnIntent.putExtra("userId", userId);
            earnIntent.putExtra("storeId", storeId);

            // Adds the Intent that starts the Activity to the top of the stack.
            stackBuilder.addNextIntent(earnIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);

        }

        // Issue the notification.
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // NotificationID allows you to update the notification later on.
        mNotificationManager.notify(0, mBuilder.build());

    }

    private class AcceptThread extends Thread {

        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {

            // Use a temporary object that is later assigned to mmServerSocket, because mmServerSocket is final
            BluetoothServerSocket tmp = null;
            try {

               // mAdapter = BluetoothAdapter.getDefaultAdapter();
               if (mAdapter == null) {
                    // Device does not support Bluetooth
                   mAdapter = BluetoothAdapter.getDefaultAdapter();
                }

                if (!mAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }

                try {
                    Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                    startActivity(discoverableIntent);
                } catch (Exception ex) {

                }

                // MY_UUID is the app's UUID string, also used by the client code.
                tmp = mAdapter.listenUsingRfcommWithServiceRecord(NAME_INSECURE, MY_UUID_INSECURE);
            } catch (IOException e) { }
            mmServerSocket = tmp;
        }

        public void run() {

            BluetoothSocket socket = null;
            // Keep listening until exception occurs or a socket is returned
            while (true) {

                try {

                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    break;
                }
                // If a connection was accepted
                if (socket != null) {

                    // Do work to manage the connection (in a separate thread) manageConnectedSocket(socket);
                    connectedThread = new ConnectedThread(socket);
                    try {
                    mmServerSocket.close();
                    } catch (IOException e) { }
                    break;
                }
            }
        }

        /** Will cancel the listening socket, and cause the thread to finish */
        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) { }
        }
    }

    private class ConnectedThread extends Thread {

        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {

            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {

                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {

            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);
                    if(bytes > 0) {

                        String incomingMsg = new String(buffer);
                        // System.out.println(derp);
                        Toast.makeText(getApplicationContext(), incomingMsg, Toast.LENGTH_LONG);
                    }

                    write("success".getBytes());

                    // Send the obtained bytes to the UI activity
                    // mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) { }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

}
