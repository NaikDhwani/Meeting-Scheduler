package com.cosc592.meetingscheduler;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class EmailManagement {

    private String senderEmail, senderPassword;

    public EmailManagement(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        senderEmail = preferences.getString("EMAIL","");
        senderPassword = preferences.getString("PASSWORD", "");
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getSenderPassword() {
        return senderPassword;
    }

    public void setSenderPassword(String senderPassword) {
        this.senderPassword = senderPassword;
    }

    //Key stored as persistent data
    public void saveEmailPreferences (Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("EMAIL",senderEmail);
        editor.putString("PASSWORD",senderPassword);
        editor.apply();
    }
}
