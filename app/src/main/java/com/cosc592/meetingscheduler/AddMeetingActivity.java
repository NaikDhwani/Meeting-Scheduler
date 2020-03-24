package com.cosc592.meetingscheduler;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddMeetingActivity extends AppCompatActivity {

    final Calendar meetingCalendar = Calendar.getInstance();
    EditText meetingDate, meetingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

        meetingDate = findViewById(R.id.meetingDate);
        meetingTime = findViewById(R.id.meetingTime);
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
                .get(Calendar.YEAR), meetingCalendar.get(Calendar.MONTH),true).show();
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
        String dateFormat = "HH:MM";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        meetingTime.setText(sdf.format(meetingCalendar.getTime()));
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
