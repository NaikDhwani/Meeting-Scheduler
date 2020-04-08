package com.cosc592.meetingscheduler;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.widget.Toast;

public class BackgroundEmailSenderClass extends AsyncTask<Void, Void, Void> {
    ProgressDialog pDialog;
    private String subject, body, recipients;
    static Context context;
    EmailManagement emailManagement;
    GMailSender sender;

    public BackgroundEmailSenderClass(Context context, String subject, String body, String recipients) {
        this.context = context;

        emailManagement = new EmailManagement(context);
        sender = new GMailSender(emailManagement.getSenderEmail(), emailManagement.getSenderPassword());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        this.subject = subject;
        this.body = body;
        this.recipients = recipients;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Sending Emails...");
        pDialog.show();
    }

    protected Void doInBackground(Void... mApi) {

        try {
            // Here we add subject, Body, your mail Id, and receiver mail Id's .
            sender.sendMail(subject, body, emailManagement.getSenderEmail(), recipients); }

        catch (Exception ex) { }
        return null;
    }
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        pDialog.cancel();
        Toast.makeText(context, "Email Sent Successfully",Toast.LENGTH_SHORT).show();
    }
}
