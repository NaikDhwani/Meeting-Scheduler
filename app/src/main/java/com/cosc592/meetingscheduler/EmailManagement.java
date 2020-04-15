//to manage the sender email and password
package com.cosc592.meetingscheduler;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class EmailManagement {
    //Declarations
    private String senderEmail, senderPassword;

    public EmailManagement(Context context) { // constructor
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        senderEmail = preferences.getString("EMAIL","");
        senderPassword = preferences.getString("PASSWORD", "");
        /*senderEmail = "";
        senderPassword = "";
        saveEmailPreferences(context);*/
    }

    public String getSenderEmail() { // returns email
        return senderEmail;
    }
    public void setSenderEmail(String senderEmail) { //sets email
        this.senderEmail = senderEmail;
    }

    public String getSenderPassword() { // returns password
        return senderPassword;
    }
    public void setSenderPassword(String senderPassword) { // sets password
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
