package com.bizzmark.servlet;

/**
 * Created by Provigil on 16-11-2016.
 */

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GCMHttp extends HttpServlet {

    private final Logger logger = Logger.getLogger(GCMNotification.class.getName());

    // API key will be found in your firebase console --> your project --> settings --> cloud messaging --> server key.
    private String API_KEY = "AIzaSyD447hNzkrGgpwTSjRjrVk4KliJn1hDPKQ";

    public GCMHttp() {
        super();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        doPost(req, resp);

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Token you will get from
        // String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        String responseStr = send("eUMdddv6KWM:APA91bFmvwnNxb9FLLoR9MCgtDU0agIZ7rqvxlrE2xDzKI1DCkF5y8M2rzlthbV13Qw9kIUAvNYw6_NB1dXk-ZuNMsWAvmjxIEZHGEjAVEWXAnIkJ3KG4XGUgW0lOo8BE70XdexpDDjj", "Hi From GCMHTTP Servlet.");

        response.getWriter().println("Response: " + responseStr);
    }

    private String send(String to, String msg) {
        String response = "";
        try {
            // Prepare JSON containing the GCM message content. What to send and where to send.
            JSONObject jGcmData = new JSONObject();
            JSONObject jData = new JSONObject();
            jData.put("message", msg);

            jGcmData.put("to", to);

            // What to send in GCM message.
            jGcmData.put("data", jData);

            // Create connection to send GCM Message request.
            URL url = new URL("https://android.googleapis.com/gcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "key=" + API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Send GCM message content.
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jGcmData.toString().getBytes());

            // Read GCM response.
            InputStream inputStream = conn.getInputStream();
            response = getStringFromInputStream(inputStream);
            System.out.println(response);
            System.out.println("Check your device/emulator for notification or logcat for " + "confirmation of the receipt of the GCM message.");
        } catch (IOException e) {

            //System.out.println("Unable to send GCM message.");
            // System.out.println("Please ensure that API_KEY has been replaced by the server " + "API key, and that the device's registration token is correct (if specified).");
            e.printStackTrace();
        }
        return response;
    }

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

}
