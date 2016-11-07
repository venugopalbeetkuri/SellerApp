/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.bizzmark.servlet;

import java.io.IOException;

import javax.servlet.http.*;

public class MyServlet extends HttpServlet {

    // Write a message to the database
     // FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference myRef = database.getReference("message");

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {


        /*resp.setContentType("text/plain");
        resp.getWriter().println("Please use the form to POST to this url");*/

        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("server/saving-data/fireblog");




       // Log.info("Got cron message, constructing email.");

        //Create a new Firebase instance and subscribe on child events.
        Firebase firebase = new Firebase("[firebase ref]");
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Build the email message contents using every field from Firebase.
                final StringBuilder newItemMessage = new StringBuilder();
                newItemMessage.append("This should arrive very closely after changing the data");


                //Now Send the email
                Properties props = new Properties();
                Session session = Session.getDefaultInstance(props, null);
                try {
                    Message msg = new MimeMessage(session);
                    //Make sure you substitute your project-id in the email From field
                    msg.setFrom(new InternetAddress("anything@[app-engine].appspotmail.com",
                            "Todo Nagger"));
                    msg.addRecipient(Message.RecipientType.TO,
                            new InternetAddress("myEmail@gmail.com", "Recipient"));
                    msg.setSubject("Feast Email Test");
                    msg.setText(newItemMessage.toString());
                    Transport.send(msg);
                } catch (MessagingException | UnsupportedEncodingException e) {
                    Log.warning(e.getMessage());
                }
            }

            public void onCancelled(FirebaseError firebaseError) {
            }
        });*/













    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String name = req.getParameter("name");
        resp.setContentType("text/plain");
        if (name == null) {

            resp.getWriter().println("Please enter a name");
        } else {

            resp.getWriter().println("Hi: " + name);
        }


       /* boolean success = connectToMongoDB();
        if (success) {
            resp.getWriter().println("Updation to server success.");
        } else {
            resp.getWriter().println("Updation to server failed.");
        }*/

    }


    private boolean connectToMongoDBAndCommit() {
        try {

           /* MongoClientURI mongoURI = new MongoClientURI("mongodb://venugopalbeetkuri:shreshta143@ds015770.mlab.com:15770/pointshub");
            MongoClient mClient = new MongoClient(mongoURI);

            MongoDatabase db = mClient.getDatabase(mongoURI.getDatabase());

            MongoCollection collection = db.getCollection("client");

            Document document = new Document();
            document.put("userId", "testUser");
            document.put("storeId", "testStore");
            document.put("billAmount", "12000");
            document.put("earned", "120");

            collection.insertOne(document);*/
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }


}
