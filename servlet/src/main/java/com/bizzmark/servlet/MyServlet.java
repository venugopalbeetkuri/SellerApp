/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.bizzmark.servlet;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.io.IOException;

import javax.servlet.http.*;

public class MyServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/plain");
        resp.getWriter().println("Please use the form to POST to this url");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String name = req.getParameter("name");
        resp.setContentType("text/plain");
        if (name == null) {

            resp.getWriter().println("Please enter a name");
        }
        resp.getWriter().println("Hello " + name);
    }


    private boolean connectToMongoDB() {
        try {

            MongoClientURI mongoURI = new MongoClientURI("mongodb://venugopalbeetkuri:shreshta143@ds015770.mlab.com:15770/pointshub");
            MongoClient mClient = new MongoClient(mongoURI);

            MongoDatabase db = mClient.getDatabase(mongoURI.getDatabase());

            MongoCollection collection = db.getCollection("client");



            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }


}
