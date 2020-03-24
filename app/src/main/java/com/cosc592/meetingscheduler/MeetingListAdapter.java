package com.cosc592.meetingscheduler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class MeetingListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<MeetingManagement> meetingList;
    TextView meetingNameText, meetingDateTimeText;
    ImageButton editMeeting, deleteMeeting;

    public MeetingListAdapter(Context context, List<MeetingManagement> meetingList) {
        layoutInflater =LayoutInflater.from(context);
        this.meetingList = meetingList;
    }

    @Override
    public int getCount() {
        return meetingList.size();
    }

    @Override
    public Object getItem(int position) {
        return meetingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.meeting_list_item, null);
                meetingNameText = convertView.findViewById(R.id.meetingName);
                meetingDateTimeText = convertView.findViewById(R.id.meetingDateTime);
                editMeeting = convertView.findViewById(R.id.editMeeting);
                deleteMeeting = convertView.findViewById(R.id.deleteMeeting);
                MeetingManagement meetingManagement = meetingList.get(position);
                EditButtonHandler editHandler =new EditButtonHandler(position);
                editMeeting.setOnClickListener(editHandler);
                DeleteButtonHandler DeleteHandler =new DeleteButtonHandler(position);
                deleteMeeting.setOnClickListener(DeleteHandler);
                meetingNameText.setText(meetingManagement.getTitle());
                meetingDateTimeText.setText(meetingManagement.getData_time());
            }else{
                convertView.getTag();
            }
        return convertView;
    }

    private class EditButtonHandler implements View.OnClickListener{

        private  int rowNumber;

        public EditButtonHandler(int rowNumber){
            this.rowNumber = rowNumber;
        }

        @Override
        public void onClick(View v) {
            Log.d("Row: ",rowNumber+"");
        }
    }

    private class DeleteButtonHandler implements View.OnClickListener{

        private  int rowNumber;

        public DeleteButtonHandler(int rowNumber){
            this.rowNumber = rowNumber;
        }

        @Override
        public void onClick(View v) {
            Log.d("Row: ",rowNumber+"");
        }
    }
}
