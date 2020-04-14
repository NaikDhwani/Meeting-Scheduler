//to display the main screen
package com.cosc592.meetingscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    //Declaration
    EditText loginKey;
    LoginManagement loginManagement;
    public static DatabaseManager dbManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DatabaseManager(this);

        loginManagement = new LoginManagement(this);

        TextChangeHandler handler = new TextChangeHandler();
        loginKey = findViewById(R.id.loginKeyEditText);
        loginKey.addTextChangedListener(handler);
    }
    //after key is entered, it automatically goes to next screen
    private class TextChangeHandler implements TextWatcher{

        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        public void afterTextChanged(Editable s) {
            if(loginKey.getText().toString().equals(loginManagement.getLoginKey())){
                Intent keyActivity = new Intent(getApplicationContext(), MemberActivity.class);
                startActivity(keyActivity);
            }
        }
    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }
}
