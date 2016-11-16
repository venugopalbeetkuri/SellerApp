/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.bizzmark.servlet;


import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GCMNotification extends HttpServlet {


    private final Logger logger = Logger.getLogger(GCMNotification.class.getName());

    private static final long serialVersionUID = 1L;

    // Put your Google API Server Key here
    private static final String GOOGLE_SERVER_KEY = "AIzaSyAQb_EE6ED9o3jWPdEuGmPz-PRhWYi5gJU";

    static final String REGISTER_NAME = "name";
    static final String MESSAGE_KEY = "message";
    static final String TO_NAME = "toName";
    static final String REG_ID_STORE = "GCMRegId.txt";

    public GCMNotification() {
        super();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        doPost(req, resp);

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String action = request.getParameter("action");

        if ("shareRegId".equalsIgnoreCase(action)) {

            writeToFile(request.getParameter("name"), request.getParameter("regId"));
            request.setAttribute("pushStatus", "GCM Name and corresponding RegId Received.");

            try {
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if ("sendMessage".equalsIgnoreCase(action)) {

            try {
                String fromName = request.getParameter(REGISTER_NAME);
                String toName = request.getParameter(TO_NAME);
                String userMessage = request.getParameter(MESSAGE_KEY);
                Sender sender = new Sender(GOOGLE_SERVER_KEY);

                Message message = new Message.Builder().timeToLive(30)
                        .delayWhileIdle(true).addData(MESSAGE_KEY, userMessage)
                        .addData(REGISTER_NAME, fromName).build();

                Map<String, String> regIdMap = readFromFile();
                String regId = regIdMap.get(toName);
                Result result = sender.send(message, regId, 1);
                request.setAttribute("pushStatus", result.toString());

            } catch (IOException ioe) {

                ioe.printStackTrace();
                request.setAttribute("pushStatus", "RegId required: " + ioe.toString());
            } catch (Exception e) {

                e.printStackTrace();
                request.setAttribute("pushStatus", e.toString());
            }

            try {
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }


        } else if ("multicast".equalsIgnoreCase(action)) {

            try {

                String fromName = request.getParameter(REGISTER_NAME);
                String userMessage = request.getParameter(MESSAGE_KEY);
                Sender sender = new Sender(GOOGLE_SERVER_KEY);

                Message message = new Message.Builder().timeToLive(30)
                        .delayWhileIdle(true).addData(MESSAGE_KEY, userMessage)
                        .addData(REGISTER_NAME, fromName).build();

                Map regIdMap = readFromFile();

                List regIdList = new ArrayList();

                Set<Map.Entry> regIdSet = regIdMap.entrySet();

                for (Map.Entry entry : regIdSet) {
                    regIdList.add(entry.getValue());
                }

                MulticastResult multiResult = sender.send(message, regIdList, 1);
                request.setAttribute("pushStatus", multiResult.toString());
            } catch (IOException ioe) {

                ioe.printStackTrace();
                request.setAttribute("pushStatus", "RegId required: " + ioe.toString());
            } catch (Exception e) {

                e.printStackTrace();
                request.setAttribute("pushStatus", e.toString());
            }

            try {

                request.getRequestDispatcher("index.jsp").forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    }

    private void writeToFile(String name, String regId) throws IOException {

        Map regIdMap = readFromFile();
        regIdMap.put(name, regId);
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(REG_ID_STORE, false)));

        Set<Map.Entry> regIdSet = regIdMap.entrySet();

        for (Map.Entry entry : regIdSet) {

            out.println(entry.getKey() + "," + entry.getValue());
        }

        out.println(name + "," + regId);
        out.close();
    }

    private Map<String, String> readFromFile() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(REG_ID_STORE));
        String regIdLine = "";
        Map regIdMap = new HashMap();

        while ((regIdLine = br.readLine()) != null) {

            String[] regArr = regIdLine.split(",");
            regIdMap.put(regArr[0], regArr[1]);
        }

        br.close();

        return regIdMap;
    }

}
