package com.rifatul.trackroom.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rifatul.trackroom.AppPrefs;
import com.rifatul.trackroom.R;
import com.rifatul.trackroom.interfaces.ApiInterface;
import com.rifatul.trackroom.models.ItemAssignments;

import java.util.List;

public class RecyclerViewAdapterAssignmemtList extends RecyclerView.Adapter<RecyclerViewAdapterAssignmemtList.ViewHolder> {

    Context context;

    public ApiInterface getApi () { return AppPrefs.getInstance(context).getApi(); }

    public String getAccess() { return AppPrefs.getInstance(context).getAccess(); }

    public String getRefresh() { return AppPrefs.getInstance(context).getRefresh(); }

    //public void showAssignment(int taskPk, String taskName, String taskMaterialLink) { AppPrefs.getInstance(context).showAssignment(taskPk, taskName, taskMaterialLink); }

    private List<ItemAssignments> assignmentList;
    public  RecyclerViewAdapterAssignmemtList(List<ItemAssignments> assignmentList) { this.assignmentList = assignmentList; }

    @NonNull
    @Override
    public RecyclerViewAdapterAssignmemtList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assignment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterAssignmemtList.ViewHolder holder, int position) {
        String taskName = assignmentList.get(position).getClassroom();
        String taskDescription = assignmentList.get(position).getDescription();
        String taskDeadline = assignmentList.get(position).getDate();
        int taskPk = assignmentList.get(position).getPk();

        holder.setData(taskName, taskDescription, taskDeadline);

        if (position%1 == 0)
            holder.cardViewLinearLayout.setBackgroundResource(R.drawable.item_class_bg1);
        if (position%2 == 1)
            holder.cardViewLinearLayout.setBackgroundResource(R.drawable.item_class_bg2);
        if (position%3 == 2)
            holder.cardViewLinearLayout.setBackgroundResource(R.drawable.item_class_bg3);
        if (position%4 == 3)
            holder.cardViewLinearLayout.setBackgroundResource(R.drawable.item_class_bg4);
        if (position%5 == 4)
            holder.cardViewLinearLayout.setBackgroundResource(R.drawable.item_class_bg5);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showAssignment(taskPk, taskName, taskMaterialLink);
            }
        });

    }

    @Override
    public int getItemCount() {
        return assignmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView assignmentTitle;
        private TextView assignmentDescription;
        private TextView assignmentDesc;
        LinearLayout cardViewLinearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            assignmentTitle = itemView.findViewById(R.id.item_assignment_title);
            assignmentDesc = itemView.findViewById(R.id.item_assignment_deadline);
            assignmentDescription = itemView.findViewById(R.id.item_assignment_description);
            cardViewLinearLayout = itemView.findViewById(R.id.layout_Class_Card);

        }
        public void setData(String taskName, String taskDescription, String taskDeadline) {
            assignmentTitle.setText(taskName);
            assignmentDescription.setText(taskDescription);
            assignmentDesc.setText(taskDeadline);
        }
    }
}
