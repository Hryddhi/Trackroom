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
import com.rifatul.trackroom.models.ItemNotification;

import java.util.List;

public class RecyclerViewAdapterUnifiedAssignmentList extends RecyclerView.Adapter<RecyclerViewAdapterUnifiedAssignmentList.ViewHolder> {

    private final List<ItemNotification> assignmentList;
    Context context;

    public ApiInterface getApi () { return AppPrefs.getInstance(context).getApi(); }

    public String getAccess() { return AppPrefs.getInstance(context).getAccess(); }

    public String getRefresh() { return AppPrefs.getInstance(context).getRefresh(); }

    //public void showAssignment(int taskPk, String taskName, String taskMaterialLink) { AppPrefs.getInstance(context).showAssignment(taskPk, taskName, taskMaterialLink); }

    public RecyclerViewAdapterUnifiedAssignmentList(List<ItemNotification> data) {
        this.assignmentList = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notifications, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String taskName = assignmentList.get(position).getClassroom();
        String taskClassName= assignmentList.get(position).getMessage();
        String taskDeadline = assignmentList.get(position).getDate_created();
        //int taskPk = assignmentList.get(position).getId();
        //String taskMaterialLink = assignmentList.get(position).getReadingMaterial();

        holder.setData(taskName, taskClassName, taskDeadline);

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

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAssignment(taskPk, taskName, taskMaterialLink);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return assignmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView assignmentTitle;
        private TextView assignmentClassName;
        private TextView assignmentDeadline;
        LinearLayout cardViewLinearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            assignmentTitle = itemView.findViewById(R.id.item_assignment_title);
            assignmentClassName = itemView.findViewById(R.id.item_assignment_class);
            assignmentDeadline = itemView.findViewById(R.id.item_assignment_deadline);
            cardViewLinearLayout = itemView.findViewById(R.id.layout_Class_Card);
        }
        public void setData(String taskName, String taskClassName, String taskDeadline) {
            assignmentTitle.setText(taskName);
            assignmentClassName.setText(taskClassName);
            assignmentDeadline.setText(taskDeadline);
        }
    }
}
