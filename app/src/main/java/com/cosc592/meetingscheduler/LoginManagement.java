//to manage the login key
package com.cosc592.meetingscheduler;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LoginManagement {
    //Declaration
    private String loginKey;

    public LoginManagement(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        loginKey = preferences.getString("KEY","admin");
    }
    //sets the key
    public void setLoginKey(String key)
    {
        this.loginKey = key;
    }
    //gets the key
    public String getLoginKey()
    {
        return loginKey;
    }

    //Key stored as persistent data
    public void saveLoginKeyPreferences (Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("KEY",loginKey);
        editor.apply();
    }
}
