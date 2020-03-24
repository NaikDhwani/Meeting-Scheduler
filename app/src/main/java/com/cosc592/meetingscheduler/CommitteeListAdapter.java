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

public class CommitteeListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<CommitteeManagement> committeeList;
    TextView committeeNameText, instituteNameText;
    ImageButton editCommittee, deleteCommittee;

    public CommitteeListAdapter(Context context, List<CommitteeManagement> committeeList) {
        layoutInflater =LayoutInflater.from(context);
        this.committeeList = committeeList;
    }

    @Override
    public int getCount() {
        return committeeList.size();
    }

    @Override
    public Object getItem(int position) {
        return committeeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.committee_list_item, null);
                committeeNameText = convertView.findViewById(R.id.committeeName);
                instituteNameText = convertView.findViewById(R.id.instituteName);
                editCommittee = convertView.findViewById(R.id.editCommittee);
                deleteCommittee = convertView.findViewById(R.id.deleteCommittee);
                CommitteeManagement committeeManagement = committeeList.get(position);
                EditButtonHandler editHandler =new EditButtonHandler(position);
                editCommittee.setOnClickListener(editHandler);
                DeleteButtonHandler DeleteHandler =new DeleteButtonHandler(position);
                deleteCommittee.setOnClickListener(DeleteHandler);
                committeeNameText.setText(committeeManagement.getTitle());
                instituteNameText.setText(committeeManagement.getInstitute_id());
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
