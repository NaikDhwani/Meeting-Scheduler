// This class sends email in the background
package com.cosc592.meetingscheduler;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.widget.Toast;
// AsyncTask allows automatic execution of methods(onPreExecute,doInBackground,onPostExecute) while this class is called
public class BackgroundEmailSenderClass extends AsyncTask<Void, Void, Void> {
    //Declaration
    ProgressDialog pDialog;
    private String subject, body, recipients;
    static Context context;
    EmailManagement emailManagement;
    GMailSender sender;
    //constructor
    public BackgroundEmailSenderClass(Context context, String subject, String body, String recipients) {
        this.context = context;
        emailManagement = new EmailManagement(context);
        sender = new GMailSender(emailManagement.getSenderEmail(), emailManagement.getSenderPassword());
        //to allow access to the email without interruptions on the network
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        this.subject = subject;
        this.body = body;
        this.recipients = recipients;
    }
    //executes as soon as class is called
    protected void onPreExecute() {
        super.onPreExecute();
    }
    //runs in background when class is called
    protected Void doInBackground(Void... mApi) {
        try {
            // Here we add subject, Body, your mail Id, and receiver mail Id's .
            sender.sendMail(subject, body, emailManagement.getSenderEmail(), recipients); }
        catch (Exception ex) {
            System.out.println("In doInBackground exception");
        }
        return null;
    }
    //After background is executed, whether it is success is displayed
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        Toast.makeText(context, "Email Sent Successfully",Toast.LENGTH_SHORT).show();
    }
}
