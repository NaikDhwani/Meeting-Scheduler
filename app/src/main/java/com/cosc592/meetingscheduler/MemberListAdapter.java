//to manage the members list
package com.cosc592.meetingscheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;

public class MemberListAdapter extends BaseAdapter {
    //Declarations
    private LayoutInflater layoutInflater;
    private List<MemberManagement> memberList;
    TextView memberNameText, memberIdText;
    ImageButton editMember, deleteMember;

    public MemberListAdapter(Context context, List<MemberManagement> memberList) {
        layoutInflater =LayoutInflater.from(context);
        this.memberList = memberList;
    }
//to get the list of memebers
    public int getCount() {
        return memberList.size();
    }

    public Object getItem(int position) {
        return memberList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
//to view edit,delete,search options
    public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.member_list_item, null);

                memberNameText = convertView.findViewById(R.id.memberName);
                memberIdText = convertView.findViewById(R.id.memberId);
                editMember = convertView.findViewById(R.id.editMember);
                deleteMember = convertView.findViewById(R.id.deleteMember);

                MemberManagement memberManagement = memberList.get(position);

                ButtonHandler handler = new ButtonHandler(position);
                editMember.setOnClickListener(handler);
                deleteMember.setOnClickListener(handler);

                memberNameText.setText(memberManagement.getFirst_name() + " " + memberManagement.getMiddle_name() + " " + memberManagement.getLast_name());
                memberIdText.setText(memberManagement.getMemberId());
            }else{
                convertView.getTag();
            }
        return convertView;
    }
//when clicked on specific operations, works accordingly
    private class ButtonHandler implements View.OnClickListener{

        private  int rowNumber;

        public ButtonHandler (int rowNumber){
            this.rowNumber = rowNumber;
        }
        public void onClick(View v) {
            MemberManagement memberManagement = memberList.get(rowNumber);
            if (v.getId() == editMember.getId())
                new MemberActivity().Update(memberManagement.getMemberId()+"");
            else if (v.getId() == deleteMember.getId())
                new MemberActivity().showDialogBox(memberManagement.getMemberId()+"");
        }
    }
}
