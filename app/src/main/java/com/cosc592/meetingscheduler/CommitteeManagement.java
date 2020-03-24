package com.cosc592.meetingscheduler;

public class CommitteeManagement {
    private String committee_id, title, description, date;
    private int institute_id;

    public CommitteeManagement(String committee_id, int institute_id, String title, String description, String date){
        this.committee_id = committee_id;
        this.institute_id = institute_id;
        this.title = String.valueOf(title.charAt(0)).toUpperCase() + title.substring(1);
        this.description = description;
        this.date = date;
    }

    public String getCommittee_id() {
        return committee_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public int getInstitute_id() {
        return institute_id;
    }
}
