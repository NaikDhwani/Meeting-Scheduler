//to update the members
package com.cosc592.meetingscheduler;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UpdateMemberActivity extends AppCompatActivity {
    //Declarations
    EditText firstName, middleName, lastName, address, zipCode, city, state, country,email, personalContact, officeContact, resContact, birthDate;
    CheckBox alive;
    Button update, cancel;
    DatabaseManager dbManager = MainActivity.dbManager;
    String memberId, dateOfBirth;
    boolean notNullCheck , validation;
    final Calendar calendar = Calendar.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        Intent intent = getIntent();
        memberId = intent.getStringExtra("memberId");

        String[] memberInfo = dbManager.getMember(memberId);

        TextView title = findViewById(R.id.title);
        title.setText("Modify Member");

        memberId = memberInfo[0];
        dateOfBirth = memberInfo[13];

        alive = findViewById(R.id.alive);
        alive.setEnabled(true);
        if(memberInfo[14].equals("1"))
            alive.setChecked(true);
        else
            alive.setChecked(false);

        firstName = findViewById(R.id.firstName);
        firstName.setEnabled(false);
        firstName.setText(memberInfo[1]);
        middleName = findViewById(R.id.middleName);
        middleName.setEnabled(false);
        middleName.setText(memberInfo[2]);
        lastName = findViewById(R.id.lastName);
        lastName.setEnabled(false);
        lastName.setText(memberInfo[3]);

        address = findViewById(R.id.address);
        address.setText(memberInfo[4]);
        zipCode = findViewById(R.id.zipCode);
        zipCode.setText(memberInfo[5]);
        city = findViewById(R.id.city);
        city.setText(memberInfo[6]);
        state = findViewById(R.id.state);
        state.setText(memberInfo[7]);
        country = findViewById(R.id.country);
        country.setText(memberInfo[8]);
        email = findViewById(R.id.email);
        email.setText(memberInfo[12]);
        personalContact = findViewById(R.id.personalContact);
        personalContact.setText(memberInfo[9]);
        officeContact = findViewById(R.id.officeContact);
        officeContact.setText(memberInfo[11]);
        resContact = findViewById(R.id.resContact);
        resContact.setText(memberInfo[10]);
        birthDate = findViewById(R.id.birthDate);
        birthDate.setText(dateOfBirth);

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
//updates the fields accordingly
    public class ButtonHandler implements View.OnClickListener{

        public void onClick(View v) {
            if(v.getId() == cancel.getId()){
                finish();
            }else{
                notNullChecking();
                validation();
                if(notNullCheck == true && validation == true){
                    try{

                        String add, member_city, member_state, member_country,
                                member_email, date_of_birth, contact_mobile, contact_residence, contact_office;
                        int zip_code, is_alive;

                        add = address.getText().toString();
                        zip_code = Integer.valueOf(zipCode.getText().toString());
                        member_city = city.getText().toString();
                        member_state = state.getText().toString();
                        member_country = country.getText().toString();
                        member_email = email.getText().toString();
                        date_of_birth = birthDate.getText().toString();
                        contact_mobile = personalContact.getText().toString();
                        contact_office = officeContact.getText().toString();
                        contact_residence = resContact.getText().toString();

                        if (alive.isChecked())
                            is_alive = 1;
                        else
                            is_alive = 0;

                        MemberManagement memberManagement = new MemberManagement(memberId, add, zip_code, member_city, member_state, member_country,
                                contact_mobile, contact_residence, contact_office, member_email, date_of_birth, is_alive);
                        dbManager.updateMember(memberManagement);

                        Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"Not Updated",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
//checks if email is valid or not
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
// checks is any of the fields are empty
    public void notNullChecking(){
        notNullCheck = true;
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
//to pick the date for dob
    public void OpenDatePicker(View view) {
        closeKeyBoard();
        DatePickerDialog datePicker = new DatePickerDialog(this, date, Integer.valueOf(dateOfBirth.substring(6)),
                Integer.valueOf(dateOfBirth.substring(0,2))-1,
                Integer.valueOf(dateOfBirth.substring(3,5)));
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        //datePicker.updateDate(Integer.valueOf(dateOfBirth.substring(6)), Integer.valueOf(dateOfBirth.substring(0,2))-1, Integer.valueOf(dateOfBirth.substring(3,5)));
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
// to convert to format
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

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }
}
