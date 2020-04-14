//to access the key to login
package com.cosc592.meetingscheduler;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class LoginKeyActivity extends AppCompatActivity {
    //Declaration
    Intent newActivity;
    LoginManagement loginManagement;
    EditText loginKeyEditText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_key);

        loginManagement = new LoginManagement(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loginKeyEditText = findViewById(R.id.loginKeyEditText);
        loginKeyEditText.setText(loginManagement.getLoginKey());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        if(Build.VERSION.SDK_INT > 11) {
            invalidateOptionsMenu();
            menu.findItem(R.id.loginKeyOption).setVisible(false);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
         /*Handle action bar item clicks here. The action bar will
         automatically handle clicks on the Home/Up button, so long
         as you specify a parent activity in AndroidManifest.xml.*/

        switch (item.getItemId()) {
            case R.id.meetingOption:
                newActivity = new Intent(this, MeetingActivity.class);
                startActivity(newActivity);
                return true;
            case R.id.committeeOption:
                newActivity = new Intent(this, CommitteeActivity.class);
                startActivity(newActivity);
                return true;
            case R.id.memberOption:
                newActivity = new Intent(this, MemberActivity.class);
                startActivity(newActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
// when clicked on cancel, goes back
    public void Cancel(View view) {
        finish();
    }
//to update the modified key value and go back
    public void Update(View view) {
        loginManagement.setLoginKey(loginKeyEditText.getText().toString());
        loginManagement.saveLoginKeyPreferences(this);
        finish();
    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }
}
