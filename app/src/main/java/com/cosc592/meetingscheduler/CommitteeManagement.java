// to manage the committee
package com.cosc592.meetingscheduler;

public class CommitteeManagement {
    //Declarations
    private String title, description, date, department;
    private int committee_id;
    //constructors
    public CommitteeManagement(String department, String title, String description, String date){
        this.department = department;
        this.title = String.valueOf(title.charAt(0)).toUpperCase() + title.substring(1);
        this.description = description;
        this.date = date;
    }
    public CommitteeManagement(int committee_id, String department, String title){
        this.committee_id = committee_id;
        this.department = department;
        this.title = String.valueOf(title.charAt(0)).toUpperCase() + title.substring(1);
    }
    public CommitteeManagement(int committee_id, String description){
        this.committee_id = committee_id;
        this.description = description;
    }
//to get the fields needed for the committee creation
    public int getCommittee_id() {
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

    public String getDepartment() {
        return department;
    }
}
