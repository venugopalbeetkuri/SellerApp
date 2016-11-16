/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.bizzmark.servlet;

import com.bizzmark.bo.Points;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.repackaged.com.google.api.client.util.IOUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.*;

public class MyServlet extends HttpServlet {

    Gson gson = null;




    private final Logger logger = Logger.getLogger(MyServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        logger.log(Level.WARNING, "In doGet method.");

        String jsonString = req.getParameter("jsonString");

        jsonString = URLDecoder.decode(jsonString, "UTF-8");
        boolean success = saveDataToDB(jsonString);

        resp.setContentType("text/plain");
        if (success) {

            resp.getWriter().println("Data: " + jsonString + "-saved successfully.");
        } else {

            resp.getWriter().println("Failed to save. Data: " + jsonString);
        }

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        logger.log(Level.INFO, "In doPost method.");
        doGet(req, resp);
    }


    private void doCalculations() {
        try {
            // Get a reference to our posts
            // final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://smartpoints-8b016.firebaseio.com//points");

// Attach a listener to read the data at our posts reference
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Post post = dataSnapshot.getValue(Post.class);
                    System.out.println("onDataChange");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private boolean saveDataToDB(String jsonData) {
        boolean success = false;
        try {

            Points points = getPoints(jsonData);
            if (null == points) {
                return success;
            }

            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

            Entity pointsEntity = new Entity("points");

            pointsEntity.setProperty("userId", points.getUserId());
            pointsEntity.setProperty("storeId", points.getStoreId());
            pointsEntity.setProperty("discountAmount", points.getDiscountAmount());
            pointsEntity.setProperty("redeemed", points.getRedeemed());
            pointsEntity.setProperty("earned", points.getEarned());
            pointsEntity.setProperty("billAmount", points.getBillAmount());

            datastore.put(pointsEntity);

            success = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return success;
    }

    private Points getPoints(String jsonData) {

        Points points = null;
        try {

            if (null == gson) {
                gson = new Gson();
            }

            points = gson.fromJson(jsonData, Points.class);

        } catch (Exception ex) {

            ex.printStackTrace();
        }
        return points;
    }

    private JSONObject getJSonObject() {

        JSONObject jsonObject = null;
        try {

            URL url = new URL("https://smartpoints-8b016.firebaseio.com/client.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer json = new StringBuffer();
            String line;

            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            reader.close();

            jsonObject = new JSONObject(json.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return jsonObject;
    }




}
