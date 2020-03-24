package com.cosc592.meetingscheduler;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText loginKey;
    LoginManagement loginManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginManagement = new LoginManagement(this);

        TextChangeHandler handler = new TextChangeHandler();
        loginKey = findViewById(R.id.loginKeyEditText);
        loginKey.addTextChangedListener(handler);
    }

    private class TextChangeHandler implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        @Override
        public void afterTextChanged(Editable s) {
            if(loginKey.getText().toString().equals(loginManagement.getLoginKey())){
                Intent keyActivity = new Intent(getApplicationContext(), MeetingActivity.class);
                startActivity(keyActivity);
            }
        }
    }
}
