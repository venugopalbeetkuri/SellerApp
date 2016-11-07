package com.bizzmark;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.bizzmark.db.EarnBO;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main2Activity extends AppCompatActivity {

    Button earnPoints = null;

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    DatabaseReference clientDatabase = database.child("client");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // database = FirebaseDatabase.getInstance().getReference();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        earnPoints = (Button) findViewById(R.id.button2);

        initEvents();
    }

    private void initEvents() {


        earnPoints.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // ResetReceiver();

                EarnBO earnBO = new EarnBO("Venu", "Venu store", "2500", "2500");

                clientDatabase.setValue(earnBO);
            }
        });
    }
}
