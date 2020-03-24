package com.cosc592.meetingscheduler;

public class CommitteeMemberManagement {

    private String committee_id, member_id;

    public CommitteeMemberManagement(String committee_id, String member_id) {
        this.committee_id = committee_id;
        this.member_id = member_id;
    }

    public String getCommittee_id() {
        return committee_id;
    }

    public String getMember_id() {
        return member_id;
    }
}
