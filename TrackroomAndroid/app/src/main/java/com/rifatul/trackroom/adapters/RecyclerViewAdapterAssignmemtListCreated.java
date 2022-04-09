package com.rifatul.trackroom.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.rifatul.trackroom.ActivityDetailedPost;
import com.rifatul.trackroom.ActivityDetailedPostCreate;
import com.rifatul.trackroom.AppPrefs;
import com.rifatul.trackroom.R;
import com.rifatul.trackroom.interfaces.ApiInterface;
import com.rifatul.trackroom.models.ItemAssignments;

import java.util.List;

public class RecyclerViewAdapterAssignmemtListCreated extends RecyclerView.Adapter<RecyclerViewAdapterAssignmemtListCreated.ViewHolder> {

    Context context;

    public ApiInterface getApi () { return AppPrefs.getInstance(context).getApi(); }

    public String getAccess() { return AppPrefs.getInstance(context).getAccess(); }

    public String getRefresh() { return AppPrefs.getInstance(context).getRefresh(); }

    //public void showAssignment(int taskPk, String taskName, String taskMaterialLink) { AppPrefs.getInstance(context).showAssignment(taskPk, taskName, taskMaterialLink); }

    private List<ItemAssignments> assignmentListCreated;
    public RecyclerViewAdapterAssignmemtListCreated(List<ItemAssignments> assignmentList) { this.assignmentListCreated = assignmentList; }

    @NonNull
    @Override
    public RecyclerViewAdapterAssignmemtListCreated.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assignment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterAssignmemtListCreated.ViewHolder holder, int position) {
        String taskName = assignmentListCreated.get(position).getTitle();
        String taskDescription = assignmentListCreated.get(position).getDescription();
        String taskDeadline = assignmentListCreated.get(position).getDate_created();
        int taskPk = assignmentListCreated.get(position).getPk();

        holder.setData(taskName, taskDescription, taskDeadline);

        if (position%1 == 0)
            holder.cardViewConstraintLayout.setBackgroundResource(R.drawable.item_class_bg1);
        if (position%2 == 1)
            holder.cardViewConstraintLayout.setBackgroundResource(R.drawable.item_class_bg2);
        if (position%3 == 2)
            holder.cardViewConstraintLayout.setBackgroundResource(R.drawable.item_class_bg3);
        if (position%4 == 3)
            holder.cardViewConstraintLayout.setBackgroundResource(R.drawable.item_class_bg4);
        if (position%5 == 4)
            holder.cardViewConstraintLayout.setBackgroundResource(R.drawable.item_class_bg5);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int postPk = assignmentListCreated.get(holder.getAdapterPosition()).getPk();
                String postTitle = assignmentListCreated.get(holder.getAdapterPosition()).getTitle();
                String postDate = assignmentListCreated.get(holder.getAdapterPosition()).getDate_created();
                String postDescription = assignmentListCreated.get(holder.getAdapterPosition()).getDescription();
                Log.d("Classroom pk on assignment list recycler view : ", String.valueOf(postPk));
                Log.d("Classroom title on assignment list recycler view : ", postTitle);
                Log.d("Classroom deadline on assignment list recycler view : ", postDate);
                Log.d("Classroom description on assignment list recycler view : ", postDescription);
                Intent detailedPostView = new Intent(v.getContext(), ActivityDetailedPostCreate.class);
                detailedPostView.putExtra("postPk", postPk);
                detailedPostView.putExtra("postTitle", postTitle);
                detailedPostView.putExtra("postDate", postDate);
                detailedPostView.putExtra("postDescription", postDescription);
                v.getContext().startActivity(detailedPostView);
                //showAssignment(taskPk, taskName, taskMaterialLink);
            }
        });

    }

    @Override
    public int getItemCount() {
        return assignmentListCreated.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView assignmentTitle;
        private TextView assignmentDescription;
        private TextView assignmentDesc;
        ConstraintLayout cardViewConstraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            assignmentTitle = itemView.findViewById(R.id.item_assignment_title);
            assignmentDesc = itemView.findViewById(R.id.item_assignment_deadline);
            assignmentDescription = itemView.findViewById(R.id.item_assignment_description);
            cardViewConstraintLayout = itemView.findViewById(R.id.layout_Post_Card);

        }
        public void setData(String taskName, String taskDescription, String taskDeadline) {
            assignmentTitle.setText(taskName);
            assignmentDescription.setText(taskDescription);
            assignmentDesc.setText(taskDeadline);
        }
    }
}
