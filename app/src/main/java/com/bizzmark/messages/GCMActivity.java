package com.bizzmark.messages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;




import com.bizzmark.R;
import com.google.firebase.iid.FirebaseInstanceId;


/**
 * Created by Venu.
 */

//this is our main activity
public class GCMActivity extends AppCompatActivity {

    private static final String TAG = "GCMActivity";

    String refreshedToken = null;

    //Creating a broadcast receiver for gcm registration

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcm);

        if (null == refreshedToken) {
            refreshedToken = FirebaseInstanceId.getInstance().getToken();
        }

        System.out.println("Refreshed token: " + refreshedToken);
    }

    //Registering receiver on activity resume
    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Refreshed token: " + refreshedToken);

    }


    //Unregistering receiver on activity paused
    @Override
    protected void onPause() {
        super.onPause();
        //Log.w("MainActivity", "onPause");

        System.out.println("Refreshed token: " + refreshedToken);

    }

}
