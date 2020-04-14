//to manage the meeting
package com.cosc592.meetingscheduler;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.LinkedList;

public class MeetingActivity extends AppCompatActivity {
    //Declaration
    Intent newActivity;
    MeetingListAdapter meetingAdapter;
    DatabaseManager dbManager = MainActivity.dbManager;
    static Context c;
    AlertDialog dialogBox;
    Button delete, cancel;
    static EditText loginKey, searchMeeting;
    static LoginManagement loginManagement;
    static ListView meetingList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        c = MeetingActivity.this;
        loginManagement = new LoginManagement(this);
        meetingList = findViewById(R.id.meetingList);

        EditActionListener handler = new EditActionListener();
        searchMeeting = findViewById(R.id.searchEditText);
        searchMeeting.addTextChangedListener(handler);
    }

    protected void onStart(){
        super.onStart();
        updateView();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(Build.VERSION.SDK_INT > 11) {
            invalidateOptionsMenu();
            menu.findItem(R.id.meetingOption).setVisible(false);
        }
        return true;
    }

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
//to add the meeting
    public void Add(View view) {
        LinkedList<CommitteeMemberManagement> list = dbManager.showAllCommitteeMember();
        if (list.size()<1){
            Toast.makeText(getApplicationContext(), "You do not have any committee with members so fist add committee with members",Toast.LENGTH_LONG).show();
        }else {
            newActivity = new Intent(this, AddMeetingActivity.class);
            startActivity(newActivity);
        }
    }
//to view the meetings
    private void updateView(){
        LinkedList<MeetingManagement> list = dbManager.showAllMeeting();
        meetingList.removeAllViewsInLayout();
        meetingAdapter = new MeetingListAdapter(c, list);
        meetingList.setAdapter(meetingAdapter);
    }
//to update the meeting
    public void Update(int meeting_id) {
        newActivity = new Intent(c, UpdateMeetingActivity.class);
        newActivity.putExtra("meetingId", meeting_id);
        c.startActivity(newActivity);
    }
//shows dialog box, to enter login key to delete the meeting
    public void showDialogBox(int meeting_id) {
        dialogBox = new AlertDialog.Builder(c).create();
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.custom_dialog, null);

        loginKey = dialogView.findViewById(R.id.loginKey);
        delete = dialogView.findViewById(R.id.delete);
        cancel = dialogView.findViewById(R.id.cancel);

        DialogBoxListener handler = new DialogBoxListener(meeting_id);
        delete.setOnClickListener(handler);
        cancel.setOnClickListener(handler);
        dialogBox.setView(dialogView);
        dialogBox.show();
    }
//when entered key, if it's correct deletes the meeting
    private class DialogBoxListener implements View.OnClickListener{
        private int id;
        public DialogBoxListener(int meeting_id){
            this.id = meeting_id;
        }

        public void onClick(View v) {

            if(v.getId() == cancel.getId()){
                dialogBox.dismiss();
            }else if(v.getId() == delete.getId()) {
                if(loginKey.getText().toString().equals(loginManagement.getLoginKey())){
                    dbManager.deleteMeeting(id);
                }
                Toast.makeText(c, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                dialogBox.dismiss();
                updateView();
            }
        }
    }
//search options for meeting
    public void searchOptions(View view) {
        PopupMenu popup = new PopupMenu(c, searchMeeting);
        popup.getMenuInflater().inflate(R.menu.meeting_searching, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                LinkedList<MeetingManagement> list = null;

                if (item.getItemId() == R.id.activeOption)
                    list = dbManager.searchMeeting("",2);
                else if (item.getItemId() == R.id.cancelOption)
                    list = dbManager.searchMeeting("",3);
                else
                    list = dbManager.showAllMeeting();

                meetingList.removeAllViewsInLayout();
                meetingAdapter = new MeetingListAdapter(c, list);
                meetingList.setAdapter(meetingAdapter);
                return true;
            }
        });
        popup.show();
    }
//when ever if a character is entered, if there are any records displays those meetings
    public class EditActionListener implements TextWatcher {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        public void afterTextChanged(Editable s) {

            if (searchMeeting.getText().toString().equals(""))
                updateView();
            else {
                String searchText = searchMeeting.getText().toString();
                LinkedList<MeetingManagement> list = dbManager.searchMeeting(searchText, 1);
                meetingList.removeAllViewsInLayout();
                meetingAdapter = new MeetingListAdapter(c, list);
                meetingList.setAdapter(meetingAdapter);
            }
        }
    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }
}
