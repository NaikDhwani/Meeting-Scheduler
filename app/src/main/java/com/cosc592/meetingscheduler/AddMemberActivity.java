package com.cosc592.meetingscheduler;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddMemberActivity extends AppCompatActivity {

    final Calendar birthdayCalendar = Calendar.getInstance();
    EditText birthDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        birthDate = findViewById(R.id.birthDate);
    }

    public void Back(View view) {
        finish();
    }

    public void OpenDatePicker(View view) {
        closeKeyBoard();
        DatePickerDialog datePicker = new DatePickerDialog(this, date, birthdayCalendar
                .get(Calendar.YEAR), birthdayCalendar.get(Calendar.MONTH),
                birthdayCalendar.get(Calendar.DAY_OF_MONTH));
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePicker.show();
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            birthdayCalendar.set(Calendar.YEAR, year);
            birthdayCalendar.set(Calendar.MONTH, month);
            birthdayCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            birthDate();
        }
    };

    private void birthDate() {
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        birthDate.setText(sdf.format(birthdayCalendar.getTime()));
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
