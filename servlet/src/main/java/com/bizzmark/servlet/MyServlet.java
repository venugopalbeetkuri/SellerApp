/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.bizzmark.servlet;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.*;

public class MyServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger(MyServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        logger.log(Level.WARNING, "In doGet method.");

        JSONObject jsonObject = getJSonObject();
        String jString = jsonObject.toString();

        String name = req.getParameter("name");
        resp.setContentType("text/plain");
        if (name == null) {

            resp.getWriter().println("Please enter a name");
        } else {

            resp.getWriter().println("Hi: " + name);
        }

        resp.getWriter().println(jString);

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        logger.log(Level.INFO, "In doPost method.");
        doGet(req, resp);
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
