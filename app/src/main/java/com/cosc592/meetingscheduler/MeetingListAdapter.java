//to manage the meetings list
package com.cosc592.meetingscheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;

public class MeetingListAdapter extends BaseAdapter {
    //Declaration
    private LayoutInflater layoutInflater;
    private List<MeetingManagement> meetingList;
    TextView meetingNameText, committeeTitle;
    ImageButton editMeeting, deleteMeeting;

    public MeetingListAdapter(Context context, List<MeetingManagement> meetingList) {
        layoutInflater =LayoutInflater.from(context);
        this.meetingList = meetingList;
    }
//to get the meetings list
    public int getCount() {
        return meetingList.size();
    }

    public Object getItem(int position) {
        return meetingList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
//able to view all delete,edit options
    public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.meeting_list_item, null);

                meetingNameText = convertView.findViewById(R.id.meetingName);
                committeeTitle = convertView.findViewById(R.id.committeeTitle);
                editMeeting = convertView.findViewById(R.id.editMeeting);
                deleteMeeting = convertView.findViewById(R.id.deleteMeeting);

                MeetingManagement meetingManagement = meetingList.get(position);

                ButtonHandler handler =new ButtonHandler(position);
                editMeeting.setOnClickListener(handler);
                deleteMeeting.setOnClickListener(handler);

                meetingNameText.setText(meetingManagement.getTitle());
                committeeTitle.setText(meetingManagement.getCommitteeName());
            }else{
                convertView.getTag();
            }
        return convertView;
    }
//to edit the meeting
    private class ButtonHandler implements View.OnClickListener{

        private  int rowNumber;

        public ButtonHandler(int rowNumber){
            this.rowNumber = rowNumber;
        }
        public void onClick(View v) {
            MeetingManagement meetingManagement = meetingList.get(rowNumber);
            if(v.getId() == R.id.editMeeting)
                new MeetingActivity().Update(meetingManagement.getMeeting_id());
            else
                new MeetingActivity().showDialogBox(meetingManagement.getMeeting_id());
        }
    }
}
