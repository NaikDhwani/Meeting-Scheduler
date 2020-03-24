package com.cosc592.meetingscheduler;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.LinkedList;

public class MemberActivity extends AppCompatActivity {

    ImageButton addMember;
    Intent newActivity;
    MemberListAdapter memberAdapter;
    public static DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        dbManager = new DatabaseManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addMember = findViewById(R.id.addMember);
        //dbmanager.deleteMember("Member01");
        //dbmanager.insertMember1();
        //dbmanager.insertMember2();
    }

    protected void onStart(){
        super.onStart();
        updateView();
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
            menu.findItem(R.id.memberOption).setVisible(false);
        }
        return true;
    }

    @Override
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
            case R.id.loginKeyOption:
                newActivity = new Intent(this, LoginKeyActivity.class);
                startActivity(newActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Add Button Click
    public void Add(View view) {
        newActivity = new Intent(this, AddMemberActivity.class);
        startActivity(newActivity);
    }

    //Show or update the all Member list
    private void updateView(){
        DatabaseManager dbManager = new DatabaseManager(this);
        LinkedList<MemberManagement> list = dbManager.showAllMember();
        ListView memberList = findViewById(R.id.memberList);
        memberList.removeAllViewsInLayout();
        memberAdapter = new MemberListAdapter(getApplicationContext(), list);
        memberList.setAdapter(memberAdapter);
    }
}
