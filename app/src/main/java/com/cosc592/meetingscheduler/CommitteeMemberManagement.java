//to manage the committee members
package com.cosc592.meetingscheduler;

public class CommitteeMemberManagement {
    //Declarations
    private String  member_id, committeeTitle;
    private int committee_id;
    //constructors
    public CommitteeMemberManagement(int committee_id, String member_id) {
        this.committee_id = committee_id;
        this.member_id = member_id;
    }
    public CommitteeMemberManagement(int committee_id, String member_id, String committeeTitle) {
        this.committee_id = committee_id;
        this.member_id = member_id;
        this.committeeTitle = committeeTitle;
    }
// to get committee id, committee title, member id
    public int getCommittee_id() {
        return committee_id;
    }

    public String getCommitteeTitle() {return committeeTitle; }

    public String getMember_id() {
        return member_id;
    }
}
