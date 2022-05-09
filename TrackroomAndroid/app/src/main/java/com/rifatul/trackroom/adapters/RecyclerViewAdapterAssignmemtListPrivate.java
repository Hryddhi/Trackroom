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

import com.rifatul.trackroom.ActivityDetailedPostCreate;
import com.rifatul.trackroom.ActivityDetailedPostPrivate;
import com.rifatul.trackroom.ActivityDetailedQuizCreate;
import com.rifatul.trackroom.ActivityDetailedQuizPrivate;
import com.rifatul.trackroom.AppPrefs;
import com.rifatul.trackroom.R;
import com.rifatul.trackroom.interfaces.ApiInterface;
import com.rifatul.trackroom.models.ItemAssignments;

import java.util.List;

public class RecyclerViewAdapterAssignmemtListPrivate extends RecyclerView.Adapter<RecyclerViewAdapterAssignmemtListPrivate.ViewHolder> {

    Context context;

    public ApiInterface getApi () { return AppPrefs.getInstance(context).getApi(); }

    public String getAccess() { return AppPrefs.getInstance(context).getAccess(); }

    public String getRefresh() { return AppPrefs.getInstance(context).getRefresh(); }

    //public void showAssignment(int taskPk, String taskName, String taskMaterialLink) { AppPrefs.getInstance(context).showAssignment(taskPk, taskName, taskMaterialLink); }

    private List<ItemAssignments> assignmentListPrivate;
    public RecyclerViewAdapterAssignmemtListPrivate(List<ItemAssignments> assignmentListPrivate) { this.assignmentListPrivate = assignmentListPrivate; }

    @NonNull
    @Override
    public RecyclerViewAdapterAssignmemtListPrivate.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assignment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterAssignmemtListPrivate.ViewHolder holder, int position) {
        String taskName = assignmentListPrivate.get(position).getTitle();
        String taskDescription = assignmentListPrivate.get(position).getDescription();
        String taskDeadline = assignmentListPrivate.get(position).getDate_created();
        String taskType = assignmentListPrivate.get(position).getPost_type();



        holder.setData(taskName, taskDescription, taskDeadline, taskType);

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
                int postPk = assignmentListPrivate.get(holder.getAdapterPosition()).getPk();
                String postTitle = assignmentListPrivate.get(holder.getAdapterPosition()).getTitle();
                String postDate = assignmentListPrivate.get(holder.getAdapterPosition()).getDate_created();
                String postDescription = assignmentListPrivate.get(holder.getAdapterPosition()).getDescription();
                String postType = assignmentListPrivate.get(holder.getAdapterPosition()).getPost_type();

                String postCreatorImage = "http://20.212.216.183" + assignmentListPrivate.get(holder.getAdapterPosition()).getCreator_image();
                Log.d(" Pk on post list recycler view : ", String.valueOf(postPk));
                Log.d(" Title on post list recycler view : ", postTitle);
                Log.d(" Deadline on post list recycler view : ", postDate);
                Log.d(" Description on post list recycler view : ", postDescription);
                Log.d(" Type on post list recycler view : ", postType);
                Log.d("  Creator image on post list recycler view : ", postCreatorImage);
                if(postType.equals("Module")) {
                    Intent detailedPostView = new Intent(v.getContext(), ActivityDetailedPostPrivate.class);
                    detailedPostView.putExtra("postPk", postPk);
                    detailedPostView.putExtra("postTitle", postTitle);
                    detailedPostView.putExtra("postDate", postDate);
                    detailedPostView.putExtra("postDescription", postDescription);
                    detailedPostView.putExtra("postType", postType);
                    detailedPostView.putExtra("postCreatorImage", postCreatorImage);
                    v.getContext().startActivity(detailedPostView);
                } else {
                    Intent detailedQuizView = new Intent(v.getContext(), ActivityDetailedQuizPrivate.class);
                    detailedQuizView.putExtra("postPk", postPk);
                    detailedQuizView.putExtra("postTitle", postTitle);
                    detailedQuizView.putExtra("postDate", postDate);
                    detailedQuizView.putExtra("postDescription", postDescription);
                    detailedQuizView.putExtra("postType", postType);
                    detailedQuizView.putExtra("postCreatorImage", postCreatorImage);
                    v.getContext().startActivity(detailedQuizView);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return assignmentListPrivate.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView assignmentTitle;
        private TextView assignmentDescription;
        private TextView assignmentDesc;
        private TextView assignmentType;
        ConstraintLayout cardViewConstraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            assignmentTitle = itemView.findViewById(R.id.item_assignment_title);
            assignmentDesc = itemView.findViewById(R.id.item_assignment_deadline);
            assignmentDescription = itemView.findViewById(R.id.item_assignment_description);
            assignmentType = itemView.findViewById(R.id.item_assignment_type);
            cardViewConstraintLayout = itemView.findViewById(R.id.layout_Post_Card);

        }
        public void setData(String taskName, String taskDescription, String taskDeadline, String taskType) {
            assignmentTitle.setText(taskName);
            assignmentDescription.setText(taskDescription);
            assignmentDesc.setText(taskDeadline);
            assignmentType.setText(taskType);
        }
    }
}
