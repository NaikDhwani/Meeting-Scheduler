package com.cosc592.meetingscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class UpdateCommitteeActivity extends AppCompatActivity {

    EditText committeeTitle, committeeDescription, department;
    DatabaseManager dbManager = MainActivity.dbManager;
    boolean notNullCheck;
    Button update, cancel;
    String committeeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_committee);

        Intent intent = getIntent();
        committeeId = intent.getStringExtra("committeeId");

        String[] committeeInfo = dbManager.getCommittee(Integer.valueOf(committeeId));

        TextView title = findViewById(R.id.title);
        title.setText("Modify Committee");

        committeeId = committeeInfo[0];
        committeeTitle = findViewById(R.id.committeeTitle);
        committeeTitle.setEnabled(false);
        committeeTitle.setText(committeeInfo[2]);
        committeeDescription = findViewById(R.id.committeeDescription);
        committeeDescription.setText(committeeInfo[3]);
        department = findViewById(R.id.committeeInstitute);
        department.setText(committeeInfo[1]);
        department.setEnabled(false);

        Button clear = findViewById(R.id.clear);
        clear.setVisibility(View.GONE);
        update = findViewById(R.id.add);
        update.setText("Update");
        cancel = findViewById(R.id.back);
        cancel.setText("Cancel");

        ButtonHandler handler = new ButtonHandler();
        update.setOnClickListener(handler);
        cancel.setOnClickListener(handler);
    }

    public class ButtonHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId() == cancel.getId()){
                finish();
            }else{
                notNullChecking();
                if(notNullCheck == true){
                    try{
                        String description = committeeDescription.getText().toString();

                        CommitteeManagement committeeManagement = new CommitteeManagement(Integer.valueOf(committeeId), description);
                        dbManager.updateCommittee(committeeManagement);

                        Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"Not Updated",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    public void notNullChecking(){
        notNullCheck = true;
        if (committeeDescription.getText().toString().equals("")) {
            committeeDescription.setError("Required");
            notNullCheck = false;
        }
    }
 }
