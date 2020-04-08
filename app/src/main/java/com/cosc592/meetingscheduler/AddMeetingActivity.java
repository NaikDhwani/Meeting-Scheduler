package com.cosc592.meetingscheduler;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

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

        list = dbManager.getCommitteeList();
        String[] committees = new String[list.size()+1];
        if (list.size() > 0){
            committees[0] = "Select Committee";
            for (int i = 0; i < list.size(); i++){
                committeeMemberManagement = list.get(i);
                committees[i+1] = committeeMemberManagement.getCommitteeTitle();
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, committees);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        committee.setAdapter(dataAdapter);

        emailManagement = new EmailManagement(this);
        if(emailManagement.getSenderEmail().equals("") || emailManagement.getSenderPassword().equals("")){
            showEmailDialogBox();
        }
    }

    private void showEmailDialogBox() {
        dialogBox = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.custom_email_dialog, null);

        email = dialogView.findViewById(R.id.email);
        password = dialogBox.findViewById(R.id.password);
        addEmail = dialogView.findViewById(R.id.addEmail);

        DialogBoxListener handler = new DialogBoxListener();
        addEmail.setOnClickListener(handler);
        dialogBox.setView(dialogView);
        dialogBox.show();
    }

    private class DialogBoxListener implements View.OnClickListener{

        @Override
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

    private void meetingDate() {
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        meetingDate.setText(sdf.format(meetingCalendar.getTime()));
    }

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

    private void meetingTime() {
        String dateFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        meetingTime.setText(sdf.format(meetingCalendar.getTime()));
    }

    public void Back(View view) {
        finish();
    }

    public void Clear(View view) {
        clear();
    }

    public void Add(View view) {
        notNullChecking();
        if (notNullCheck == true){
            try {

                String title, address, meetingCity, meetingState, meetingCountry, date_of_meeting, time_of_meeting, meetingAgenda, meetingNote;
                int committee_id, zip_code;

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

                MeetingManagement meetingManagement = new MeetingManagement(committee_id, title, address, zip_code, meetingCity,
                        meetingState, meetingCountry, date_of_meeting + " " + time_of_meeting, 1, meetingAgenda, meetingNote);
                dbManager.addMeeting(meetingManagement);

                Toast.makeText(getApplicationContext(), "Successfully Added", Toast.LENGTH_SHORT).show();

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

                    String body="Dear Member," +
                            "%3C%2Fbr%3E" +
                            "%3C%2Fbr%3E" +
                            "You have a meeting for " + meetingAgenda + "%3C%2Fbr%3E%3C%2Fbr%3E" +
                            "Date: " + date_of_meeting + "%3C%2Fbr%3E%3C%2Fbr%3E" +
                            "Time: " + time_of_meeting + "%3C%2Fbr%3E%3C%2Fbr%3E" +
                            "Address: " + address + "%3C%2Fbr%3E%3C%2Fbr%3E" +
                            "Note: " + meetingNote + "%3C%2Fbr%3E%3C%2Fbr%3E" +
                            "Thank You.";
                    new BackgroundEmailSenderClass(getApplicationContext(), title +" Meeting", body, email).execute(); }
                catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "Email Not Sent Successfully", Toast.LENGTH_SHORT).show();
                }
                //sendEmail(email,title, date_of_meeting, time_of_meeting, address,meetingAgenda, meetingNote);

                clear();

            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"Insert Valid Inputs",Toast.LENGTH_SHORT).show();
            }
        }
    }

/*    public void sendEmail(String email, String title, String Date, String Time, String Add, String Agenda, String Note){
        String subject= title + " Meeting";
        String body="Dear Member," +
                "%3C%2Fbr%3E" +
                "%3C%2Fbr%3E" +
                "You have a meeting for " + Agenda + "%3C%2Fbr%3E%3C%2Fbr%3E" +
                "Date: " + Date + "%3C%2Fbr%3E%3C%2Fbr%3E" +
                "Time: " + Time + "%3C%2Fbr%3E%3C%2Fbr%3E" +
                "Address: " + Add + "%3C%2Fbr%3E%3C%2Fbr%3E" +
                "Note: " + Note + "%3C%2Fbr%3E%3C%2Fbr%3E" +
                "Thank You.";
        String mailTo = "mailto:" + email +
                "?&subject=" + Uri.encode(subject) +
                "&body=" + Uri.encode(body);
        Intent emailIntent = new Intent(Intent.ACTION_VIEW);
        emailIntent.setData(Uri.parse(mailTo));
        startActivity(emailIntent);
    }*/

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
}
