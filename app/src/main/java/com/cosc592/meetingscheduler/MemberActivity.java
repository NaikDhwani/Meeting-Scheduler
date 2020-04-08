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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.LinkedList;

public class MemberActivity extends AppCompatActivity {

    ImageButton addMember, sortMember;
    Intent newActivity;
    static MemberListAdapter memberAdapter;
    static ListView memberList;
    DatabaseManager dbManager = MainActivity.dbManager;
    static Context c;
    AlertDialog dialogBox;
    Button delete, cancel;
    static EditText loginKey, searchMember;
    static LoginManagement loginManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        c = MemberActivity.this;
        loginManagement = new LoginManagement(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addMember = findViewById(R.id.addMember);
        sortMember = findViewById(R.id.sortButton);

        EditActionListener handler = new EditActionListener();
        searchMember = findViewById(R.id.searchEditText);
        searchMember.addTextChangedListener(handler);

        memberList = findViewById(R.id.memberList);
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

    //Update Button Click
    public void Update(String memberId){
        newActivity = new Intent(c, UpdateMemberActivity.class);
        newActivity.putExtra("memberId", memberId);
        c.startActivity(newActivity);
    }

    //Show or update the all Member list
    private void updateView(){
        LinkedList<MemberManagement> list = dbManager.showAllMember();
        memberList.removeAllViewsInLayout();
        memberAdapter = new MemberListAdapter(c, list);
        memberList.setAdapter(memberAdapter);
    }

    //Delete DialogBox
    public void showDialogBox(String memberId) {
        dialogBox = new AlertDialog.Builder(c).create();
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.custom_dialog, null);

        loginKey = dialogView.findViewById(R.id.loginKey);
        delete = dialogView.findViewById(R.id.delete);
        cancel = dialogView.findViewById(R.id.cancel);

        DialogBoxListener handler = new DialogBoxListener(memberId);
        delete.setOnClickListener(handler);
        cancel.setOnClickListener(handler);
        dialogBox.setView(dialogView);
        dialogBox.show();
    }

    public class DialogBoxListener implements View.OnClickListener{
        private String id;
        public DialogBoxListener(String memberId){
            this.id = memberId;
        }

        @Override
        public void onClick(View v) {

            if(v.getId() == cancel.getId()){
                dialogBox.dismiss();
            }else if(v.getId() == delete.getId()) {
                if(loginKey.getText().toString().equals(loginManagement.getLoginKey())){
                    dbManager.deleteMember(id);
                    Toast.makeText(c, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(c, "Login Key is Not Valid", Toast.LENGTH_SHORT).show();
                }
                dialogBox.dismiss();
                updateView();
            }
        }
    }

    public void Sort(View view) {
        PopupMenu popup = new PopupMenu(c, sortMember);
        popup.getMenuInflater().inflate(R.menu.member_sorting, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                LinkedList<MemberManagement> list = null;

                if (item.getItemId() == R.id.first)
                    list = dbManager.sortMembers(1);
                else if (item.getItemId() == R.id.last)
                    list = dbManager.sortMembers(2);
                else if (item.getItemId() == R.id.regDes)
                    list = dbManager.sortMembers(3);
                else
                    list = dbManager.sortMembers(4);

                memberList.removeAllViewsInLayout();
                memberAdapter = new MemberListAdapter(c, list);
                memberList.setAdapter(memberAdapter);
                return true;
            }
        });
        popup.show();
    }

    public void searchOptions(View view) {
        PopupMenu popup = new PopupMenu(c, searchMember);
        popup.getMenuInflater().inflate(R.menu.member_searching, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                LinkedList<MemberManagement> list = null;

                if (item.getItemId() == R.id.alive)
                    list = dbManager.searchMember("",2);
                else if (item.getItemId() == R.id.notAlive)
                    list = dbManager.searchMember("",3);
                else
                    list = dbManager.showAllMember();

                memberList.removeAllViewsInLayout();
                memberAdapter = new MemberListAdapter(c, list);
                memberList.setAdapter(memberAdapter);
                return true;
            }
        });
        popup.show();
    }

    public class EditActionListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        @Override
        public void afterTextChanged(Editable s) {
            if (searchMember.getText().toString().equals(""))
                updateView();
            else {
                String searchText = searchMember.getText().toString();
                LinkedList<MemberManagement> list = dbManager.searchMember(searchText, 1);
                memberList.removeAllViewsInLayout();
                memberAdapter = new MemberListAdapter(c, list);
                memberList.setAdapter(memberAdapter);
            }
        }
    }
}
