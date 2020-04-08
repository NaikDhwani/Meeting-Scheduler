package com.cosc592.meetingscheduler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class CommitteeMemberListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private String[] ids;
    private String idList;
    TextView memberNameText;
    ImageButton deleteMember;
    DatabaseManager dbManager = MainActivity.dbManager;

    public CommitteeMemberListAdapter(Context context, String[] ids, String idList) {
        layoutInflater =LayoutInflater.from(context);
        this.ids = ids;
        this.idList =idList;
    }

    @Override
    public int getCount() {
        return ids.length;
    }

    @Override
    public Object getItem(int position) {
        return ids[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.committee_member_list_item, null);

                memberNameText = convertView.findViewById(R.id.memberName);
                deleteMember = convertView.findViewById(R.id.deleteMember);

                ButtonHandler handler = new ButtonHandler(position);
                deleteMember.setOnClickListener(handler);

                String memberName = dbManager.getMemberName(ids[position]);
                if(!memberName.equals(""))
                    memberNameText.setText(memberName);
                else
                    memberNameText.setText("No More Exist");
            }else{
                convertView.getTag();
            }
        return convertView;
    }

    private class ButtonHandler implements View.OnClickListener{

        private  int rowNumber;

        public ButtonHandler (int rowNumber){
            this.rowNumber = rowNumber;
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == deleteMember.getId()) {
                Log.d("before", idList);
                if (rowNumber == 0) {
                    if (ids.length == 1){
                        idList = idList.replace(String.valueOf(ids[rowNumber]), "");
                    }else {
                        idList = idList.replace(ids[rowNumber]+",", "");
                    }
                } else
                    idList = idList.replace("," + ids[rowNumber], "");
                Log.d("after", idList);
                new ViewMemberActivity().Update(idList);
            }
        }
    }
}
