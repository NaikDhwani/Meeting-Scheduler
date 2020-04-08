package com.cosc592.meetingscheduler;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddMemberActivity extends AppCompatActivity {

    final Calendar calendar = Calendar.getInstance();
    EditText firstName, middleName, lastName, address, zipCode, city, state, country, email, personalContact, officeContact, resContact, birthDate;
    DatabaseManager dbManager = MainActivity.dbManager;
    boolean notNullCheck, validation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        firstName = findViewById(R.id.firstName);
        middleName = findViewById(R.id.middleName);
        lastName = findViewById(R.id.lastName);
        address = findViewById(R.id.address);
        zipCode = findViewById(R.id.zipCode);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        country = findViewById(R.id.country);
        email = findViewById(R.id.email);
        personalContact = findViewById(R.id.personalContact);
        officeContact = findViewById(R.id.officeContact);
        resContact = findViewById(R.id.resContact);
        birthDate = findViewById(R.id.birthDate);
    }

    public void Back(View view) {
        finish();
    }

    public void Clear(View view) {
        clear();
    }

    public void Add(View view) {
        notNullChecking();
        validation();
        if(validation == true && notNullCheck == true) {
            try {
                String lastId = dbManager.getLastMember();

                String memberId, first_name, middle_name, last_name, add, member_city, member_state, member_country,
                        member_email, date_of_birth, registration_date, contact_mobile, contact_residence, contact_office;
                int zip_code, is_alive;
                SimpleDateFormat simpledateformat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                first_name = firstName.getText().toString();
                middle_name = middleName.getText().toString();
                last_name = lastName.getText().toString();
                add = address.getText().toString();
                zip_code = Integer.valueOf(zipCode.getText().toString());
                member_city = city.getText().toString();
                member_state = state.getText().toString();
                member_country = country.getText().toString();
                member_email = email.getText().toString();
                date_of_birth = birthDate.getText().toString();
                //Log.d("Birth Date",date_of_birth);
                registration_date = simpledateformat.format(calendar.getTime());
                contact_mobile = personalContact.getText().toString();
                contact_office = officeContact.getText().toString();
                contact_residence = resContact.getText().toString();
                is_alive = 1;

                if (lastId.equals(""))
                    memberId = first_name.substring(0,1).toUpperCase() + last_name.substring(0,1).toUpperCase() + "1";
                else {
                    String tempStr = lastId.substring(2);
                    int tempInt = Integer.valueOf(tempStr);
                    memberId = first_name.substring(0,1).toUpperCase() + last_name.substring(0,1).toUpperCase() +  (tempInt+1);
                }

                Log.d("Id",memberId);
                Log.d("Time",registration_date);

                MemberManagement memberManagement = new MemberManagement(memberId, first_name, middle_name, last_name, add, zip_code, member_city, member_state, member_country,
                        contact_mobile, contact_residence, contact_office, member_email, date_of_birth, is_alive, registration_date);
                dbManager.addMember(memberManagement);

                Toast.makeText(getApplicationContext(), "Successfully Added", Toast.LENGTH_SHORT).show();
                clear();
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"Insert Valid Inputs",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void validation() {
        validation = true;
        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            validation = false;
            email.setError("Invalid");
        }

        if (personalContact.getText().toString().length() < 10) {
            validation = false;
            personalContact.setError("Invalid");
        }
    }

    public void clear(){
        firstName.setText("");
        middleName.setText("");
        lastName.setText("");
        address.setText("");
        zipCode.setText("");
        city.setText("");
        state.setText("");
        country.setText("");
        email.setText("");
        personalContact.setText("");
        officeContact.setText("");
        resContact.setText("");
        birthDate.setText("");
    }

    public void notNullChecking(){
        notNullCheck = true;
        if (firstName.getText().toString().equals("")) {
            firstName.setError("Required");
            notNullCheck = false;
        }

        if (lastName.getText().toString().equals("")) {
            lastName.setError("Required");
            notNullCheck =false;
        }

        if (address.getText().toString().equals("")) {
            address.setError("Required");
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

        if (email.getText().toString().equals("")) {
            email.setError("Required");
            notNullCheck =false;
        }

        if (personalContact.getText().toString().equals("")) {
            personalContact.setError("Required");
            notNullCheck =false;
        }

        if (birthDate.getText().toString().equals("")) {
            birthDate.setError("Required");
            notNullCheck =false;
        }
    }

    public void OpenDatePicker(View view) {
        closeKeyBoard();
        DatePickerDialog datePicker = new DatePickerDialog(this, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePicker.show();
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            birthDate();
        }
    };

    private void birthDate() {
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        birthDate.setText(sdf.format(calendar.getTime()));
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
