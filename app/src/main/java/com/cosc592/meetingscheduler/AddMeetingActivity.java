// adds meeting and sends email to the members who need to attend the meeting
package com.cosc592.meetingscheduler;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Locale;

public class AddMeetingActivity extends AppCompatActivity {
    //declarations
    final Calendar meetingCalendar = Calendar.getInstance();
    EditText meetingTitle, meetingAddress, zipCode, city, state, country, meetingDate, meetingTime, agenda, note;
    Spinner committee;
    DatabaseManager dbManager = MainActivity.dbManager;
    boolean notNullCheck;
    static LinkedList<CommitteeMemberManagement> list;
    static CommitteeMemberManagement committeeMemberManagement;
    EmailManagement emailManagement;
    EditText email, password;
    Button addEmail;
    AlertDialog dialogBox;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        //accessed from xml to display what to fill
        meetingTitle = findViewById(R.id.meetingTitle);
        committee = findViewById(R.id.committeeSpinner);
        meetingAddress = findViewById(R.id.address);
        zipCode = findViewById(R.id.zipCode);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        country = findViewById(R.id.country);
        meetingDate = findViewById(R.id.meetingDate);
        meetingTime = findViewById(R.id.meetingTime);
        agenda = findViewById(R.id.agenda);
        note = findViewById(R.id.note);
        // committee list available to add for the meetings
        list = dbManager.getCommitteeList();
        String[] committees = new String[list.size()+1];
        if (list.size() > 0){ //if list of committees is not empty
            committees[0] = "Select Committee";
            for (int i = 0; i < list.size(); i++){
                committeeMemberManagement = list.get(i);
                committees[i+1] = committeeMemberManagement.getCommitteeTitle();
            }
        }
        //committees present are present in the drop down, select the committee required for the meeting
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, committees);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        committee.setAdapter(dataAdapter);
        emailManagement = new EmailManagement(this);
        if(emailManagement.getSenderEmail().equals("") || emailManagement.getSenderPassword().equals("")){
            showEmailDialogBox(); // to send email
        }
    }
    // dialog box displays which asks for the sender email and password
    private void showEmailDialogBox() {
        dialogBox = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.custom_email_dialog, null);
        email = dialogView.findViewById(R.id.email);
        password = dialogView.findViewById(R.id.password);
        addEmail = dialogView.findViewById(R.id.addEmail);
        DialogBoxListener handler = new DialogBoxListener();
        addEmail.setOnClickListener(handler);
        dialogBox.setView(dialogView);
        dialogBox.show();
    }
    //when clicked on button in the dialog box, gets to this method and collects sender email and password
    private class DialogBoxListener implements View.OnClickListener{
        public void onClick(View v) {
            if(v.getId() == addEmail.getId()){
                if(email.getText().toString().equals("") || password.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Please Enter Required Details", Toast.LENGTH_SHORT).show();
                } else {
                    if (Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                        emailManagement.setSenderEmail(email.getText().toString());
                        emailManagement.setSenderPassword(password.getText().toString());
                        emailManagement.saveEmailPreferences(getApplicationContext());
                        Toast.makeText(getApplicationContext(), "Successfully Added", Toast.LENGTH_SHORT).show();
                        dialogBox.dismiss();
                    }else {
                        email.setError("Not Valid");
                    }
                }
            }
        }
    }
// to pick the date
    public void OpenDatePicker(View view) {
        closeKeyBoard();
        DatePickerDialog datePicker = new DatePickerDialog(this, date, meetingCalendar
                .get(Calendar.YEAR), meetingCalendar.get(Calendar.MONTH),
                meetingCalendar.get(Calendar.DAY_OF_MONTH));
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
// after selected from date picker, converted to MM/dd/yy format of meeting date
    private void meetingDate() {
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        meetingDate.setText(sdf.format(meetingCalendar.getTime()));
    }
    // to pick the time
    public void OpenTimePicker(View view) {
        closeKeyBoard();
        new TimePickerDialog(this, time, meetingCalendar
                .get(Calendar.HOUR_OF_DAY), meetingCalendar.get(Calendar.MINUTE),true).show();
    }

    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            meetingCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            meetingCalendar.set(Calendar.MINUTE, minute);
            meetingTime();
        }
    };
    // after selected from time picker, converted to HH:mm format of meeting time
    private void meetingTime() {
        String dateFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        meetingTime.setText(sdf.format(meetingCalendar.getTime()));
    }
//when clicked on back button, goes to the main page of meeting
    public void Back(View view) {
        finish();
    }
//to clear the filled details
    public void Clear(View view) {
        clear();
    }
// adds the meeting to assigned committee and sends an email to all members in the selected committee
    public void Add(View view) {
        notNullChecking();
        if (notNullCheck == true){
            try {
                String title, address, meetingCity, meetingState, meetingCountry, date_of_meeting, time_of_meeting, meetingAgenda, meetingNote;
                int committee_id, zip_code;
                //entering details
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
                //to select committee
                committeeMemberManagement = list.get(committee.getSelectedItemPosition()-1);
                committee_id = committeeMemberManagement.getCommittee_id();

                MeetingManagement meetingManagement = new MeetingManagement(committee_id, title, address, zip_code, meetingCity,
                        meetingState, meetingCountry, date_of_meeting + " " + time_of_meeting, 1, meetingAgenda, meetingNote);
                dbManager.addMeeting(meetingManagement);

                Toast.makeText(getApplicationContext(), "Successfully Added", Toast.LENGTH_SHORT).show();

                try {
                    String idList = dbManager.getAllCommitteeMember(committee_id);
                    //list of emails added gets from the database to the particular committee
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
                    //email message body
                    String body="Dear Member," +
                            "\n\r" +
                            "\n\r"+
                            "You have a meeting for " + meetingAgenda + "\n\r" +
                            "Date: " + date_of_meeting + "\n\r" +
                            "Time: " + time_of_meeting + "\n\r" +
                            "Address: " + address + "\n\r" +
                            "Note: " + meetingNote + "\n\r" +
                            "Thank You.";
                    //sends to BackgroundEmailSender class
                    new BackgroundEmailSenderClass(getApplicationContext(), title +" Meeting", body, email).execute(); }
                catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "Email Not Sent Successfully", Toast.LENGTH_SHORT).show();
                }
                clear();

            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"Insert Valid Inputs",Toast.LENGTH_SHORT).show();
            }
        }
    }
//to clear the filled details
    private void clear() {
        meetingTitle.setText("");
        committee.setSelection(0);
        meetingAddress.setText("");
        zipCode.setText("");
        city.setText("");
        state.setText("");
        country.setText("");
        meetingDate.setText("");
        meetingTime.setText("");
        agenda.setText("");
        note.setText("");
    }
// if any values are not entered, displays as required
    public void notNullChecking(){
        notNullCheck = true;
        if (meetingTitle.getText().toString().equals("")) {
            meetingTitle.setError("Required");
            notNullCheck = false;
        }
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
        if (meetingTitle.getText().toString().equals("")) {
            meetingTitle.setError("Required");
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
