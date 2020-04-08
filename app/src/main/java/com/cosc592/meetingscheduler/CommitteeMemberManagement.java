package com.cosc592.meetingscheduler;

public class CommitteeMemberManagement {

    private String  member_id, committeeTitle;
    private int committee_id;

    public CommitteeMemberManagement(int committee_id, String member_id) {
        this.committee_id = committee_id;
        this.member_id = member_id;
    }

    public CommitteeMemberManagement(int committee_id, String member_id, String committeeTitle) {
        this.committee_id = committee_id;
        this.member_id = member_id;
        this.committeeTitle = committeeTitle;
    }

    public int getCommittee_id() {
        return committee_id;
    }

    public String getCommitteeTitle() {return committeeTitle; }

    public String getMember_id() {
        return member_id;
    }
}
