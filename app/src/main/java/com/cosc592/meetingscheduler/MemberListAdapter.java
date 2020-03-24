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

public class MemberListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<MemberManagement> memberList;
    TextView memberNameText, memberIdText;
    ImageButton editMember, deleteMember;

    public MemberListAdapter(Context context, List<MemberManagement> memberList) {
        layoutInflater =LayoutInflater.from(context);
        this.memberList = memberList;
    }

    @Override
    public int getCount() {
        return memberList.size();
    }

    @Override
    public Object getItem(int position) {
        return memberList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.member_list_item, null);
                memberNameText = convertView.findViewById(R.id.memberName);
                memberIdText = convertView.findViewById(R.id.memberId);
                editMember = convertView.findViewById(R.id.editMember);
                deleteMember = convertView.findViewById(R.id.deleteMember);
                MemberManagement memberManagement = memberList.get(position);
                EditButtonHandler editHandler =new EditButtonHandler(position);
                editMember.setOnClickListener(editHandler);
                DeleteButtonHandler DeleteHandler =new DeleteButtonHandler(position);
                deleteMember.setOnClickListener(DeleteHandler);
                memberNameText.setText(memberManagement.getFirst_name() + " " + memberManagement.getMiddle_name() + " " + memberManagement.getLast_name());
                memberIdText.setText(memberManagement.getMemberId());
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
