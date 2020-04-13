// to add the committee with the required members
package com.cosc592.meetingscheduler;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddCommitteeActivity extends AppCompatActivity {
    //Declarations
    final Calendar calendar = Calendar.getInstance();
    EditText committeeTitle, committeeDescription, committeeInstitute;
    DatabaseManager dbManager = MainActivity.dbManager;
    boolean notNullCheck;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_committee);
        //accessing from xml
        committeeTitle = findViewById(R.id.committeeTitle);
        committeeDescription = findViewById(R.id.committeeDescription);
        committeeInstitute = findViewById(R.id.committeeInstitute);
    }
//when clicked on back, goes back to the main page of committee
    public void Back(View view) {
        finish();
    }
//clears the values filled
    public void Clear(View view) {
        clear();
    }
//when clicked on add, the values entered will be added
    public void Add(View view) {
        notNullChecking();
        if (notNullCheck == true){
            try{
                SimpleDateFormat simpledateformat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                String title = committeeTitle.getText().toString();
                String description = committeeDescription.getText().toString();
                String institute = committeeInstitute.getText().toString();

                CommitteeManagement committeeManagement = new CommitteeManagement(institute, title, description, simpledateformat.format(calendar.getTime()));
                dbManager.addCommittee(committeeManagement);
                Toast.makeText(getApplicationContext(), "Successfully Added", Toast.LENGTH_SHORT).show();
                clear();
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"Insert Valid Inputs",Toast.LENGTH_SHORT).show();
            }
        }
    }
// checks if any of the values are not entered
    public void notNullChecking(){
        notNullCheck = true;
        if (committeeTitle.getText().toString().equals("")) {
            committeeTitle.setError("Required");
            notNullCheck = false;
        }
        if (committeeDescription.getText().toString().equals("")) {
            committeeDescription.setError("Required");
            notNullCheck = false;
        }
        if (committeeInstitute.getText().toString().equals("")) {
            committeeInstitute.setError("Required");
            notNullCheck = false;
        }
    }
//to clear the values entered
    public void clear(){
        committeeTitle.setText("");
        committeeDescription.setText("");
        committeeInstitute.setSelection(0);
    }
}
