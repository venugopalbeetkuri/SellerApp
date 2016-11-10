package com.bizzmark.servlet;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Venu gopal on 10-11-2016.
 */

public class DatabaseHelper {

    private static boolean persistenceEnable = false;
    private static DatabaseReference mDatabase;


    public static boolean isPersistenceEnable() {
        return persistenceEnable;
    }

    public static DatabaseReference getInstance() {

        if (mDatabase == null) {

            mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://smartpoints-8b016.firebaseio.com/client");
            if (persistenceEnable == true) {

                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            }
        }

        return mDatabase;
    }
}
