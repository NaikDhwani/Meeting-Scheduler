//to manage committee list
package com.cosc592.meetingscheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.LinkedList;
import java.util.List;

public class CommitteeListAdapter extends BaseAdapter {
    //Declarations
    private LayoutInflater layoutInflater;
    private List<CommitteeManagement> committeeList;
    TextView committeeNameText, departmentText;
    ImageButton editCommittee, deleteCommittee, viewMember;
    DatabaseManager dbManager = MainActivity.dbManager;
    static Context con;

    public CommitteeListAdapter(Context context, List<CommitteeManagement> committeeList) {
        layoutInflater =LayoutInflater.from(context);
        this.committeeList = committeeList;
        this.con = context;
    }
    // to get how many committees are present
    public int getCount() {
        return committeeList.size();
    }
    //to know exact committee list
    public Object getItem(int position) {
        return committeeList.get(position);
    }
    //index position
    public long getItemId(int position) {
        return position;
    }
// to view all data in the committee
    public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.committee_list_item, null);

                committeeNameText = convertView.findViewById(R.id.committeeName);
                departmentText = convertView.findViewById(R.id.instituteName);
                editCommittee = convertView.findViewById(R.id.editCommittee);
                deleteCommittee = convertView.findViewById(R.id.deleteCommittee);
                viewMember = convertView.findViewById(R.id.viewMember);

                CommitteeManagement committeeManagement = committeeList.get(position);

                ButtonHandler handler =new ButtonHandler(position);
                editCommittee.setOnClickListener(handler);
                deleteCommittee.setOnClickListener(handler);
                viewMember.setOnClickListener(handler);

                committeeNameText.setText(committeeManagement.getTitle());
                departmentText.setText(committeeManagement.getDepartment());
            }else{
                convertView.getTag();
            }
        return convertView;
    }
//when clicked on edit, delete to perform required operations
    private class ButtonHandler implements View.OnClickListener {

        private int rowNumber;

        public ButtonHandler(int rowNumber) {
            this.rowNumber = rowNumber;
        }

        public void onClick(View v) {
            CommitteeManagement committeeManagement = committeeList.get(rowNumber);
            if (v.getId() == editCommittee.getId())
                new CommitteeActivity().Update(committeeManagement.getCommittee_id()+"");
            else if (v.getId() == deleteCommittee.getId())
                new CommitteeActivity().showDialogBox(committeeManagement.getCommittee_id()+"");
            else if(v.getId() == viewMember.getId()){
                LinkedList<MemberManagement> list = dbManager.showAllMember();
                if(list.size()>0)
                    new CommitteeActivity().ViewMember(committeeManagement.getCommittee_id(),committeeManagement.getTitle());
                else
                    Toast.makeText(con,"Add Members First",Toast.LENGTH_LONG).show();
            }
        }
    }
}
