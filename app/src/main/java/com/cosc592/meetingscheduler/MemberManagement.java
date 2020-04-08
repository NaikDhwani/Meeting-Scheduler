package com.cosc592.meetingscheduler;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MemberManagement {

    private String memberId, first_name, middle_name, last_name, address, city, state, country, email, date_of_birth, registration_date, contact_mobile, contact_residence, contact_office;
    int zip_code, is_alive;

    public MemberManagement (String memberId, String first_name, String middle_name, String last_name,
                             String address, int zip_code, String city, String state, String country,
                             String contact_mobile, String contact_residence, String contact_office, String email,
                             String date_of_birth, int is_alive, String registration_date){
        this.memberId = memberId;
        this.first_name = String.valueOf(first_name.charAt(0)).toUpperCase() + first_name.substring(1);
        if (!middle_name.equals(""))
            this.middle_name = String.valueOf(middle_name.charAt(0)).toUpperCase() + middle_name.substring(1);
        else
            this.middle_name = middle_name;
        this.last_name = String.valueOf(last_name.charAt(0)).toUpperCase() + last_name.substring(1);
        this.address = address;
        this.zip_code = zip_code;
        this.city = String.valueOf(city.charAt(0)).toUpperCase() + city.substring(1);
        this.state = state;
        this.country = country;
        this.contact_mobile = contact_mobile;
        this.contact_residence = contact_residence;
        this.contact_office = contact_office;
        this.email = email;
        this.date_of_birth = date_of_birth;
        this.is_alive = is_alive;
        this.registration_date = registration_date;
    }

    public MemberManagement(String memberId, String address, int zip_code, String city, String state, String country,
                            String contact_mobile, String contact_residence, String contact_office, String email,
                            String date_of_birth, int is_alive){
        this.memberId = memberId;
        this.address = address;
        this.zip_code = zip_code;
        this.city = String.valueOf(city.charAt(0)).toUpperCase() + city.substring(1);
        this.state = state;
        this.country = country;
        this.contact_mobile = contact_mobile;
        this.contact_residence = contact_residence;
        this.contact_office = contact_office;
        this.email = email;
        this.date_of_birth = date_of_birth;
        this.is_alive = is_alive;
    }

    public MemberManagement(String memberId, String first_name, String middle_name, String last_name){
        this.memberId = memberId;
        this.first_name = String.valueOf(first_name.charAt(0)).toUpperCase() + first_name.substring(1);
        if (!middle_name.equals(""))
            this.middle_name = String.valueOf(middle_name.charAt(0)).toUpperCase() + middle_name.substring(1);
        else
            this.middle_name = middle_name;
        this.last_name = String.valueOf(last_name.charAt(0)).toUpperCase() + last_name.substring(1);
    }

    public MemberManagement(String memberId, String first_name, String middle_name, String last_name, String email){
        this.memberId = memberId;
        this.first_name = String.valueOf(first_name.charAt(0)).toUpperCase() + first_name.substring(1);
        if (!middle_name.equals(""))
            this.middle_name = String.valueOf(middle_name.charAt(0)).toUpperCase() + middle_name.substring(1);
        else
            this.middle_name = middle_name;
        this.last_name = String.valueOf(last_name.charAt(0)).toUpperCase() + last_name.substring(1);
        this.email = email;
    }

    public String getMemberId(){
        return memberId;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public int getZip_code() {
        return zip_code;
    }

    public String  getContact_mobile() {
        return contact_mobile;
    }

    public String getContact_residence() {
        return contact_residence;
    }

    public String getContact_office() {
        return contact_office;
    }

    public int getIs_alive() {
        return is_alive;
    }
}
