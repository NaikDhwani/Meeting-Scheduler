package com.cosc592.meetingscheduler;

public class MeetingManagement {

    private String  title, address, city, state, country, data_time, agenda, note, committeeName;
    private int committee_id, meeting_id, zip_code, is_active;

    public MeetingManagement(int committee_id, String title, String address, int zip_code, String city, String state, String country,
                             String data_time, int is_active, String agenda, String note) {
        this.committee_id = committee_id;
        this.title = String.valueOf(title.charAt(0)).toUpperCase() + title.substring(1);
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.data_time = data_time;
        this.agenda = agenda;
        this.note = note;
        this.zip_code = zip_code;
        this.is_active = is_active;
    }

    public MeetingManagement(int meeting_id, String title, String name) {
        this.meeting_id = meeting_id;
        this.title = title;
        this.committeeName = name;
    }

    public MeetingManagement(int meetingId, int committee_id, String address, int zip_code, String city, String state, String country,
                             String data_time, int is_active, String agenda, String note) {
        this.committee_id = committee_id;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.data_time = data_time;
        this.agenda = agenda;
        this.note = note;
        this.zip_code = zip_code;
        this.is_active = is_active;
        this.meeting_id = meetingId;
    }

    public int getCommittee_id() {
        return committee_id;
    }

    public String getCommitteeName(){
        return committeeName;
    }

    public String getTitle() {
        return title;
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

    public String getData_time() {
        return data_time;
    }

    public String getAgenda() {
        return agenda;
    }

    public String getNote() {
        return note;
    }

    public int getMeeting_id() {
        return meeting_id;
    }

    public int getZip_code() {
        return zip_code;
    }

    public int getIs_active() {
        return is_active;
    }
}
