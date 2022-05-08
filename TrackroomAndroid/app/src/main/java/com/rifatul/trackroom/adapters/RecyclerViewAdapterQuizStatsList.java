package com.rifatul.trackroom.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.rifatul.trackroom.ActivityViewResultCreated;
import com.rifatul.trackroom.AppPrefs;
import com.rifatul.trackroom.R;
import com.rifatul.trackroom.interfaces.ApiInterface;
import com.rifatul.trackroom.models.QuizStatsTeacher;
import com.rifatul.trackroom.models.TakeQuizQuestions;

import java.util.List;

public class RecyclerViewAdapterQuizStatsList extends RecyclerView.Adapter<RecyclerViewAdapterQuizStatsList.ViewHolder> {

    Context context;


    public ApiInterface getApi () { return AppPrefs.getInstance(context).getApi(); }

    public String getAccess() { return AppPrefs.getInstance(context).getAccess(); }

    public String getRefresh() { return AppPrefs.getInstance(context).getRefresh(); }

    //public void showAssignment(int taskPk, String taskName, String taskMaterialLink) { AppPrefs.getInstance(context).showAssignment(taskPk, taskName, taskMaterialLink); }

    private List<QuizStatsTeacher> quizStatsList;
    public RecyclerViewAdapterQuizStatsList(List<QuizStatsTeacher> quizStatsList) { this.quizStatsList = quizStatsList; }

    @NonNull
    @Override
    public RecyclerViewAdapterQuizStatsList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz_stats, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterQuizStatsList.ViewHolder holder, int position) {
        String userName = quizStatsList.get(position).getSubscriber();
        boolean hasAttended = quizStatsList.get(position).isHas_attended();
        String grade = quizStatsList.get(position).getGrade();



        holder.setData(userName, hasAttended);

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
               // int postPk = quizQuestionList.get(holder.getAdapterPosition()).getPk();
                String userName = quizStatsList.get(holder.getAdapterPosition()).getSubscriber();
                boolean hasAttended = quizStatsList.get(holder.getAdapterPosition()).isHas_attended();
                String grade = quizStatsList.get(holder.getAdapterPosition()).getGrade();
                //Log.d(" Pk on post list recycler view : ", String.valueOf(postPk));
                Log.d(" User name on Quiz stats: ", userName);
                Log.d(" Has attended on Quiz stats: ", String.valueOf(hasAttended));
                Log.d(" Quiz grade on Quiz stats : ", grade);
                 Intent detailedPostView = new Intent(v.getContext(), ActivityViewResultCreated.class);
                    detailedPostView.putExtra("userName", userName);
                    detailedPostView.putExtra("hasAttended", hasAttended);
                    detailedPostView.putExtra("grade", grade);
                    v.getContext().startActivity(detailedPostView);
            }
        });

    }

    @Override
    public int getItemCount() {
        return quizStatsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView user_name, quiz_has_attended;
        ConstraintLayout cardViewConstraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.user_name);
            quiz_has_attended = itemView.findViewById(R.id.quiz_has_attended);
            cardViewConstraintLayout = itemView.findViewById(R.id.layout_post_comment);


        }

        @SuppressLint("SetTextI18n")
        public void setData(String userName, boolean hasAttended) {
            user_name.setText(userName);
            if(hasAttended){
                quiz_has_attended.setText("Attended");

            } else {
                quiz_has_attended.setText("Not Attended");
            }



        }
    }
}
