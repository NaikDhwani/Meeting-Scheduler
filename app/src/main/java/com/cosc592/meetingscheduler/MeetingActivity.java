package com.cosc592.meetingscheduler;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MeetingActivity extends AppCompatActivity {

    Intent newActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(Build.VERSION.SDK_INT > 11) {
            invalidateOptionsMenu();
            menu.findItem(R.id.meetingOption).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         /*Handle action bar item clicks here. The action bar will
         automatically handle clicks on the Home/Up button, so long
         as you specify a parent activity in AndroidManifest.xml.*/

        switch (item.getItemId()) {
            case R.id.memberOption:
                newActivity = new Intent(this, MemberActivity.class);
                startActivity(newActivity);
                return true;
            case R.id.committeeOption:
                newActivity = new Intent(this, CommitteeActivity.class);
                startActivity(newActivity);
                return true;
            case R.id.loginKeyOption:
                newActivity = new Intent(this, LoginKeyActivity.class);
                startActivity(newActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void Add(View view) {
        newActivity = new Intent(this, AddMemberActivity.class);
        startActivity(newActivity);
    }
}
