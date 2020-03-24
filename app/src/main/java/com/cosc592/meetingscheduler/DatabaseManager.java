package com.cosc592.meetingscheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;

public class DatabaseManager extends SQLiteOpenHelper {

    //Database Name and Version
    private static final String DATABASE_NAME = "PEO_MEMBER_DATABASE";
    private static final int DATABASE_VERSION = 1;

    //Table Names
    private static final String INSTITUTE_TABLE_NAME = "INSTITUTE";
    private static final String MEMBER_TABLE_NAME = "MEMBER";
    private static final String COMMITTEE_TABLE_NAME = "COMMITTEE";
    private static final String COMMITTEE_MEMBER_TABLE_NAME = "COMMITTEE_MEMBER";
    private static final String MEETING_TABLE_NAME = "MEETING";

    //Create Table Strings
    private static final String memberTable = "create table " + MEMBER_TABLE_NAME + "(member_id TEXT PRIMARY KEY, first_name TEXT NOT NULL, middle_name TEXT, last_name TEXT NOT NULL, " +
            "address TEXT NOT NULL, zip_code INTEGER NOT NULL, city TEXT NOT NULL, state TEXT NOT NULL, country TEXT NOT NULL, contact_mobile TEXT NOT NULL, " +
            "contact_residence TEXT, contact_office TEXT, email TEXT NOT NULL, date_of_birth TEXT NOT NULL, is_alive INTEGER NOT NULL, registration_date TEXT DEFAULT CURRENT_TIMESTAMP NOT NULL, UNIQUE(contact_mobile, email))";
    private static final String committeeTable = "create table " + COMMITTEE_TABLE_NAME + "(committee_id TEXT PRIMARY KEY, institute_id INTEGER NOT NULL, title TEXT NOT NULL, " +
            "description TEXT NOT NULL, date TEXT DEFAULT CURRENT_TIMESTAMP NOT NULL, FOREIGN KEY (institute_id) REFERENCES " + INSTITUTE_TABLE_NAME + " (institute_id))";
    private static final String committee_memberTable = "create table " + COMMITTEE_MEMBER_TABLE_NAME + "(committee_id TEXT NOT NULL, member_id TEXT NOT NULL, " +
            "FOREIGN KEY (committee_id) REFERENCES " + COMMITTEE_TABLE_NAME + " (committee_id), " +
            "FOREIGN KEY (member_id) REFERENCES " + MEMBER_TABLE_NAME + " (member_id))";
    private static final String meetingTable = "create table " + MEETING_TABLE_NAME + "(meeting_id INTEGER PRIMARY KEY AUTOINCREMENT, committee_id TEXT NOT NULL, title TEXT NOT NULL, " +
            "address TEXT NOT NULL, zip_code INTEGER NOT NULL, city TEXT NOT NULL, state TEXT NOT NULL, country TEXT NOT NULL, data_time TEXT NOT NULL, is_active INTEGER NOT NULL, " +
            "agenda TEXT NOT NULL, note TEXT, FOREIGN KEY (committee_id) REFERENCES " + COMMITTEE_TABLE_NAME + " (committee_id))";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(memberTable);
        db.execSQL(committeeTable);
        db.execSQL(committee_memberTable);
        db.execSQL(meetingTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    //All Member List
    public LinkedList<MemberManagement> showAllMember(){
        SQLiteDatabase db = getWritableDatabase();
        LinkedList<MemberManagement> list = new LinkedList<>();
        Cursor cursor = db.query(MEMBER_TABLE_NAME, new String[]{"member_id", "first_name", "middle_name", "last_name", "address", "zip_code", "city", "state", "country",
                "contact_mobile", "contact_residence", "contact_office", "email", "date_of_birth", "is_alive", "registration_date"},null,null,null,null,null);
        while (cursor.moveToNext()){
            String member_id = cursor.getString(0);
            String first_name = cursor.getString(1);
            String middle_name = cursor.getString(2);
            String last_name = cursor.getString(3);
            String address = cursor.getString(4);
            int zip_code = cursor.getInt(5);
            String city = cursor.getString(6);
            String state = cursor.getString(7);
            String country = cursor.getString(8);
            String contact_mobile = cursor.getString(9);
            String contact_residence = cursor.getString(10);
            String contact_office = cursor.getString(11);
            String email = cursor.getString(12);
            String date_of_birth = cursor.getString(13);
            int is_alive = cursor.getInt(14);
            String registration_date = cursor.getString(15);
            MemberManagement memberManagement = new MemberManagement(member_id, first_name, middle_name, last_name, address, zip_code, city, state, country,
                    contact_mobile, contact_residence, contact_office, email, date_of_birth, is_alive, registration_date);
            list.addLast(memberManagement);
        }
        cursor.close();
        db.close();
        return list;
    }

    //Add a new Member
    public void addMember(MemberManagement memberManagement){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("member_id", memberManagement.getMemberId());
        row.put("first_name", memberManagement.getFirst_name());
        row.put("middle_name", memberManagement.getMiddle_name());
        row.put("last_name", memberManagement.getLast_name());
        row.put("address", memberManagement.getAddress());
        row.put("zip_code", memberManagement.getZip_code());
        row.put("city", memberManagement.getCity());
        row.put("state", memberManagement.getState());
        row.put("country", memberManagement.getCountry());
        row.put("contact_mobile", memberManagement.getContact_mobile());
        row.put("contact_residence", memberManagement.getContact_residence());
        row.put("contact_office", memberManagement.getContact_office());
        row.put("email", memberManagement.getEmail());
        row.put("date_of_birth", memberManagement.getDate_of_birth());
        row.put("is_alive", 1);
        db.insert(MEMBER_TABLE_NAME,null,row);
        db.close();
    }

/*    public void insertMember1(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO " + MEMBER_TABLE_NAME + " (member_id, first_name, middle_name, last_name, " +
                "address, zip_code, city, state, country, contact_mobile, contact_residence, contact_office, email, " +
                "date_of_birth, is_alive, registration_date) VALUES ('Member01', 'Dhwnai', 'M', 'Naik','address 123','48187', 'Canton', 'MI','USA', " +
                "'1234567890', '', '', 'brcm@peo.edu', 'sep-1-1990','1','march-19-2020');");
        db.close();
    }

    public void insertMember2(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO " + MEMBER_TABLE_NAME + " (member_id, first_name, middle_name, last_name, " +
                "address, zip_code, city, state, country, contact_mobile, contact_residence, contact_office, email, " +
                "date_of_birth, is_alive, registration_date) VALUES ('Member02', 'Dhwnai', 'M', 'Naik','address 123','48187', 'Canton', 'MI','USA', " +
                "'0987654321', '', '', 'dmn@peo.edu', 'sep-1-1990','1','march-19-2020');");
        db.close();
    }*/

    //Update existing Member
    public void updateMember(MemberManagement memberManagement){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("first_name", memberManagement.getFirst_name());
        row.put("middle_name", memberManagement.getMiddle_name());
        row.put("last_name", memberManagement.getLast_name());
        row.put("address", memberManagement.getAddress());
        row.put("zip_code", memberManagement.getZip_code());
        row.put("city", memberManagement.getCity());
        row.put("state", memberManagement.getState());
        row.put("country", memberManagement.getCountry());
        row.put("contact_mobile", memberManagement.getContact_mobile());
        row.put("contact_residence", memberManagement.getContact_residence());
        row.put("contact_office", memberManagement.getContact_office());
        row.put("email", memberManagement.getEmail());
        row.put("date_of_birth", memberManagement.getDate_of_birth());
        row.put("is_alive", memberManagement.getIs_alive());
        db.update(MEMBER_TABLE_NAME,row," member_id = ?", new String[]{memberManagement.getMemberId()});
        db.close();
    }

    //Delete selected Member
    public void deleteMember(String memberId){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(MEMBER_TABLE_NAME,"member_id = ?",new String[]{memberId});
        db.close();
    }

    //All Committee List
    public LinkedList<CommitteeManagement> showAllCommittee(){
        SQLiteDatabase db = getWritableDatabase();
        LinkedList<CommitteeManagement> list = new LinkedList<>();
        Cursor cursor = db.query(COMMITTEE_TABLE_NAME, new String[]{"committee_id", "institute_id", "name", "address", "contact_number", "email"},null,null,null,null,null);
        while (cursor.moveToNext()){
            String committee_id = cursor.getString(0);
            int institute_id = cursor.getInt(1);
            String title = cursor.getString(2);
            String description = cursor.getString(3);
            String date = cursor.getString(4);
            CommitteeManagement committeeManagement = new CommitteeManagement(committee_id, institute_id, title, description, date);
            list.addLast(committeeManagement);
        }
        cursor.close();
        db.close();
        return list;
    }

    //Add a new Committee
    public void addCommittee(CommitteeManagement committeeManagement){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("institute_id", committeeManagement.getInstitute_id());
        row.put("title", committeeManagement.getTitle());
        row.put("description", committeeManagement.getDescription());
        row.put("date", committeeManagement.getDate());
        db.insert(COMMITTEE_TABLE_NAME,null,row);
        db.close();
    }

    //Update existing Committee
    public void updateCommittee(CommitteeManagement committeeManagement){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("institute_id", committeeManagement.getInstitute_id());
        row.put("title", committeeManagement.getTitle());
        row.put("description", committeeManagement.getDescription());
        db.update(COMMITTEE_TABLE_NAME,row," committee_id = ?", new String[]{committeeManagement.getCommittee_id()});
        db.close();
    }

    //Delete selected Committee
    public void deleteCommittee(String committeeId){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(COMMITTEE_TABLE_NAME,"committee_id = ?",new String[]{committeeId});
        db.close();
    }

    public LinkedList<CommitteeMemberManagement> showAllCommitteeMember(){
        SQLiteDatabase db = getWritableDatabase();
        LinkedList<CommitteeMemberManagement> list = new LinkedList<>();
        Cursor cursor = db.query(COMMITTEE_MEMBER_TABLE_NAME, new String[]{"committee_id", "member_id"},null,null,null,null,null);
        while (cursor.moveToNext()){
            String committee_id = cursor.getString(0);
            String institute_id = cursor.getString(1);
            CommitteeMemberManagement committeeMemberManagement = new CommitteeMemberManagement(committee_id, institute_id);
            list.addLast(committeeMemberManagement);
        }
        cursor.close();
        db.close();
        return list;
    }

    //Add a new Committee Member
    public void addCommitteeMember(CommitteeMemberManagement committeeMemberManagement){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("committee_id", committeeMemberManagement.getCommittee_id());
        row.put("member_id", committeeMemberManagement.getMember_id());
        db.insert(COMMITTEE_MEMBER_TABLE_NAME,null,row);
        db.close();
    }

    //Update existing Committee Member
    public void updateCommitteeMember(CommitteeMemberManagement committeeMemberManagement){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("member_id", committeeMemberManagement.getMember_id());
        db.update(COMMITTEE_MEMBER_TABLE_NAME,row," committee_id = ?", new String[]{committeeMemberManagement.getCommittee_id()});
        db.close();
    }

    //Delete selected Committee Member
    public void deleteCommitteeMember(String committeeId){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(COMMITTEE_MEMBER_TABLE_NAME,"committee_id = ?",new String[]{committeeId});
        db.close();
    }

    //All Meeting List
    public LinkedList<MeetingManagement> showAllMeeting(){
        SQLiteDatabase db = getWritableDatabase();
        LinkedList<MeetingManagement> list = new LinkedList<>();
        Cursor cursor = db.query(MEETING_TABLE_NAME, new String[]{"meeting_id", "committee_id", "title", "address", "zip_code", "city", "state", "country",
                "data_time", "is_active", "agenda", "note"},null,null,null,null,null);
        while (cursor.moveToNext()){
            int meeting_id = cursor.getInt(0);
            String committee_id = cursor.getString(1);
            String title = cursor.getString(2);
            String address = cursor.getString(3);
            int zip_code = cursor.getInt(4);
            String city = cursor.getString(5);
            String state = cursor.getString(6);
            String country = cursor.getString(7);
            String data_time = cursor.getString(8);
            int is_active = cursor.getInt(9);
            String agenda = cursor.getString(10);
            String note = cursor.getString(11);
            MeetingManagement meetingManagement = new MeetingManagement(meeting_id, committee_id, title, address, zip_code, city, state, country,
                    data_time, is_active, agenda, note);
            list.addLast(meetingManagement);
        }
        cursor.close();
        db.close();
        return list;
    }

    //Add a new Meeting
    public void addMeeting(MeetingManagement meetingManagement){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("committee_id", meetingManagement.getCommittee_id());
        row.put("title", meetingManagement.getTitle());
        row.put("address", meetingManagement.getAddress());
        row.put("zip_code", meetingManagement.getZip_code());
        row.put("city", meetingManagement.getCity());
        row.put("state", meetingManagement.getState());
        row.put("country", meetingManagement.getCountry());
        row.put("data_time", meetingManagement.getData_time());
        row.put("is_active", meetingManagement.getIs_active());
        row.put("agenda", meetingManagement.getAgenda());
        row.put("note", meetingManagement.getNote());
        db.insert(MEETING_TABLE_NAME,null,row);
        db.close();
    }

    //Update existing Meeting
    public void updateMeeting(MeetingManagement meetingManagement){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("committee_id", meetingManagement.getCommittee_id());
        row.put("title", meetingManagement.getTitle());
        row.put("address", meetingManagement.getAddress());
        row.put("zip_code", meetingManagement.getZip_code());
        row.put("city", meetingManagement.getCity());
        row.put("state", meetingManagement.getState());
        row.put("country", meetingManagement.getCountry());
        row.put("data_time", meetingManagement.getData_time());
        row.put("is_active", meetingManagement.getIs_active());
        row.put("agenda", meetingManagement.getAgenda());
        row.put("note", meetingManagement.getNote());
        db.update(MEETING_TABLE_NAME,row," meeting_id = ?", new String[]{String.valueOf(meetingManagement.getMeeting_id())});
        db.close();
    }

    //Delete selected Meeting
    public void deleteMeeting(int meetingId){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(MEETING_TABLE_NAME,"meeting_id = ?",new String[]{String.valueOf(meetingId)});
        db.close();
    }
}
