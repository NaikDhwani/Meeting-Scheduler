//to update the meeting
package com.cosc592.meetingscheduler;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Locale;

public class UpdateMeetingActivity extends AppCompatActivity {
    //Declarations
    final Calendar meetingCalendar = Calendar.getInstance();
    EditText meetingTitle, meetingAddress, zipCode, city, state, country, meetingDate, meetingTime, agenda, note;
    Spinner committee;
    Button update, cancel;
    DatabaseManager dbManager = MainActivity.dbManager;
    int meetingId;
    boolean notNullCheck;
    static LinkedList<CommitteeMemberManagement> list;
    static CommitteeMemberManagement committeeMemberManagement;
    RadioButton activeRadio, cancelRadio;
    static String dateString, timeString;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

        Intent intent = getIntent();
        meetingId = intent.getIntExtra("meetingId",0);

        TextView title = findViewById(R.id.title);
        title.setText("Modify Meeting");

        String[] meetingInfo = dbManager.getMeeting(meetingId);

        meetingTitle = findViewById(R.id.meetingTitle);
        meetingTitle.setText(meetingInfo[2]);
        meetingTitle.setEnabled(false);

        meetingAddress = findViewById(R.id.address);
        meetingAddress.setText(meetingInfo[3]);
        zipCode = findViewById(R.id.zipCode);
        zipCode.setText(meetingInfo[4]);
        city = findViewById(R.id.city);
        city.setText(meetingInfo[5]);
        state = findViewById(R.id.state);
        state.setText(meetingInfo[6]);
        country = findViewById(R.id.country);
        country.setText(meetingInfo[7]);

        meetingDate = findViewById(R.id.meetingDate);
        dateString = meetingInfo[8].substring(0,meetingInfo[8].indexOf(' '));
        meetingDate.setText(dateString);
        meetingTime = findViewById(R.id.meetingTime);
        timeString = meetingInfo[8].substring(meetingInfo[8].indexOf(' ')+1);
        meetingTime.setText(timeString);

        agenda = findViewById(R.id.agenda);
        agenda.setText(meetingInfo[10]);
        note = findViewById(R.id.note);
        note.setText(meetingInfo[11]);

        activeRadio = findViewById(R.id.active);
        activeRadio.setEnabled(true);
        cancelRadio = findViewById(R.id.cancel);
        cancelRadio.setEnabled(true);
        if(Integer.valueOf(meetingInfo[9]) == 1)
            activeRadio.setChecked(true);
        else
            cancelRadio.setChecked(true);

        committee = findViewById(R.id.committeeSpinner);
        list = dbManager.getCommitteeList();
        String[] committees = new String[list.size()+1];
        int position = 0;
        if (list.size() > 0){
            committees[0] = "Select Committee";
            for (int i = 0; i < list.size(); i++){
                committeeMemberManagement = list.get(i);
                if (Integer.valueOf(meetingInfo[1]) == committeeMemberManagement.getCommittee_id())
                    position = i+1;
                committees[i+1] = committeeMemberManagement.getCommitteeTitle();
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, committees);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        committee.setAdapter(dataAdapter);
        committee.setSelection(position);

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
//updates the meeting fields and sends email
    private class ButtonHandler implements View.OnClickListener{

        public void onClick(View v) {
            if(v.getId() == cancel.getId()){
                finish();
            }else{
                notNullChecking();
                if(notNullCheck == true){
                    try{
                        String title, address, meetingCity, meetingState, meetingCountry, date_of_meeting, time_of_meeting, meetingAgenda, meetingNote;
                        int committee_id, zip_code, status;

                        title = meetingTitle.getText().toString();
                        address = meetingAddress.getText().toString();
                        zip_code = Integer.valueOf(zipCode.getText().toString());
                        meetingCity = city.getText().toString();
                        meetingState = state.getText().toString();
                        meetingCountry = country.getText().toString();
                        date_of_meeting = meetingDate.getText().toString();
                        time_of_meeting = meetingTime.getText().toString();
                        meetingAgenda = agenda.getText().toString();
                        meetingNote = note.getText().toString();

                        committeeMemberManagement = list.get(committee.getSelectedItemPosition()-1);
                        committee_id = committeeMemberManagement.getCommittee_id();

                        if(activeRadio.isChecked() == true)
                            status = 1;
                        else
                            status = 0;

                        MeetingManagement meetingManagement = new MeetingManagement(meetingId, committee_id, address, zip_code, meetingCity,
                                meetingState, meetingCountry, date_of_meeting + " " + time_of_meeting, status, meetingAgenda, meetingNote);
                        dbManager.updateMeeting(meetingManagement);

                        Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_SHORT).show();

                        try {
                            String idList = dbManager.getAllCommitteeMember(committee_id);
                            String[] ids = idList.split(",");
                            String email = "";
                            for (int i =0; i<ids.length;i++){
                                if (!ids[i].equals("")) {
                                    if (i == 0)
                                        email = dbManager.getEmail(ids[i]);
                                    else
                                        email = email + "," + dbManager.getEmail(ids[i]);
                                }
                            }

                            String stat="";
                            if (status == 1) stat = "Active";
                            else stat = "Cancel";

                            String body="Dear Member," +
                                    "\n\r" +
                                    "\n\r" +
                                    "You have a meeting for " + meetingAgenda + "\n\r" +
                                    "Date: " + date_of_meeting + "\n\r" +
                                    "Time: " + time_of_meeting + "\n\r" +
                                    "Address: " + address + "\n\r" +
                                    "Note: " + meetingNote + "\n\r" +
                                    "Status: " + stat + "\n\r" +
                                    "Thank You.";
                            new BackgroundEmailSenderClass(getApplicationContext(), title +" Meeting Update", body, email).execute(); }
                        catch (Exception ex) {
                            Toast.makeText(getApplicationContext(), "Email Not Sent Successfully", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"Not Updated",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
//to pick the date
    public void OpenDatePicker(View view) {
        closeKeyBoard();
        DatePickerDialog datePicker = new DatePickerDialog(this, date, Integer.valueOf(meetingDate.getText().toString().substring(6)),
                Integer.valueOf(meetingDate.getText().toString().substring(0,2))-1,
                Integer.valueOf(meetingDate.getText().toString().substring(3,5)));
        datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePicker.show();
    }
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            meetingCalendar.set(Calendar.YEAR, year);
            meetingCalendar.set(Calendar.MONTH, month);
            meetingCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            meetingDate();
        }
    };
//to convert to the format
    private void meetingDate() {
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        meetingDate.setText(sdf.format(meetingCalendar.getTime()));
    }
//to pick time
    public void OpenTimePicker(View view) {
        closeKeyBoard();
        new TimePickerDialog(this, time, Integer.valueOf(meetingTime.getText().toString().substring(0,2)),
                Integer.valueOf(meetingTime.getText().toString().substring(4)),true).show();
    }
    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            meetingCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            meetingCalendar.set(Calendar.MINUTE, minute);
            meetingTime();
        }
    };
//to set the format
    private void meetingTime() {
        String dateFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        meetingTime.setText(sdf.format(meetingCalendar.getTime()));
    }
//to check if any of the values are not entered
    public void notNullChecking(){
        notNullCheck = true;
        if (committee.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(),"Committee Selection is Required",Toast.LENGTH_LONG).show();
            notNullCheck =false;
        }
        if (meetingAddress.getText().toString().equals("")) {
            meetingAddress.setError("Required");
            notNullCheck =false;
        }
        if (zipCode.getText().toString().equals("")) {
            zipCode.setError("Required");
            notNullCheck =false;
        }
        if (city.getText().toString().equals("")) {
            city.setError("Required");
            notNullCheck =false;
        }
        if (state.getText().toString().equals("")) {
            state.setError("Required");
            notNullCheck =false;
        }
        if (country.getText().toString().equals("")) {
            country.setError("Required");
            notNullCheck =false;
        }
        if (meetingDate.getText().toString().equals("")) {
            meetingDate.setError("Required");
            notNullCheck =false;
        }
        if (meetingTime.getText().toString().equals("")) {
            meetingTime.setError("Required");
            notNullCheck =false;
        }
        if (agenda.getText().toString().equals("")) {
            agenda.setError("Required");
            notNullCheck =false;
        }
    }

    //Method to close keyboard onClick
    private void closeKeyBoard(){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }
}
