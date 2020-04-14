//to view the members
package com.cosc592.meetingscheduler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.LinkedList;

public class ViewMemberActivity extends AppCompatActivity {
    //Declarations
    AutoCompleteTextView searchMember;
    static LinkedList<MemberManagement> list;
    static MemberManagement memberManagement;
    DatabaseManager dbManager = MainActivity.dbManager;
    static int committeeId;
    String title, memberId;
    static String idList;
    static Context c;
    static ListView cmList;
    static CommitteeMemberListAdapter cmAdapter;
    static String[] ids;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_member);

        c = ViewMemberActivity.this;

        Intent intent = getIntent();
        committeeId = intent.getIntExtra("committee_id",0);
        title = intent.getStringExtra("title");

        TextView committeeTitle = findViewById(R.id.committeeTitle);
        committeeTitle.setText(title + " Committee");

        cmList = findViewById(R.id.cmList);

        searchMember = findViewById(R.id.searchMember);
        list = dbManager.showAllMember();
        String[] members = new String[list.size()];
        if (list.size() > 0){
            for (int i = 0; i < list.size(); i++){
                memberManagement = list.get(i);
                members[i] = memberManagement.getFirst_name() + " " + memberManagement.getMiddle_name() + " " + memberManagement.getLast_name();
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, members);
        searchMember.setAdapter(dataAdapter);
    }

    protected void onStart() {
        super.onStart();
        updateView();
    }
//to view the members
    private void updateView() {
        if(dbManager.isMember(committeeId) == true) {
            idList = dbManager.getAllCommitteeMember(committeeId);
            ids = idList.split(",");
            if (ids.length > 0) {
                cmList.removeAllViewsInLayout();
                cmAdapter = new CommitteeMemberListAdapter(c, ids, idList, committeeId);
                cmList.setAdapter(cmAdapter);
            }
        }else{
            cmList.removeAllViewsInLayout();
        }
    }
//when clicked on back, goes to the main page of the activity
    public void Back(View view) {
        finish();
    }
//adds member to the database
    public void Add(View view) {

        if (list.size() > 0){
            for (int i = 0; i < list.size(); i++){
                memberManagement = list.get(i);
                if (searchMember.getText().toString().equals(memberManagement.getFirst_name() + " " + memberManagement.getMiddle_name() + " " + memberManagement.getLast_name()))
                    memberId = memberManagement.getMemberId();
            }
        }
        if (dbManager.alreadyExist(committeeId) == false){
            //add
            CommitteeMemberManagement cm = new CommitteeMemberManagement(committeeId,memberId);
            dbManager.addCommitteeMember(cm);
            //updateView
            updateView();
        } else{
            //update
            if(dbManager.isMember(committeeId) == true){
                if(dbManager.alreadyExistMember(memberId) == false){
                    idList = idList + "," + memberId;
                    Update(idList);
                }else {
                    Toast.makeText(c,"Already Exist",Toast.LENGTH_LONG).show();
                }
            }else{
                idList = memberId;
                Update(idList);
            }
        }
    }
//to update member fields
    public void Update(String idList){
        CommitteeMemberManagement cm = new CommitteeMemberManagement(committeeId,idList);
        dbManager.updateCommitteeMember(cm);
        updateView();
    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }
}
