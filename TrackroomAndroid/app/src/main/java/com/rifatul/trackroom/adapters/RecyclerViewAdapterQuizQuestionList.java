package com.rifatul.trackroom.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.rifatul.trackroom.ActivityDetailedPostCreate;
import com.rifatul.trackroom.ActivityDetailedQuizPrivate;
import com.rifatul.trackroom.ActivityTakeQuiz;
import com.rifatul.trackroom.AppPrefs;
import com.rifatul.trackroom.R;
import com.rifatul.trackroom.interfaces.ApiInterface;
import com.rifatul.trackroom.models.Answers;
import com.rifatul.trackroom.models.ItemAssignments;
import com.rifatul.trackroom.models.QuizAnswer;
import com.rifatul.trackroom.models.TakeQuizQuestions;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterQuizQuestionList extends RecyclerView.Adapter<RecyclerViewAdapterQuizQuestionList.ViewHolder> {

    Context context;
    ArrayList<String> quizAnswer = new ArrayList<>();
    //ArrayList<QuizAnswer> quizAnswer = new ArrayList<>();
    boolean isOnTextChanged = false;
    AppCompatButton submitQuiz;
    View rootView;

    public ApiInterface getApi () { return AppPrefs.getInstance(context).getApi(); }

    public String getAccess() { return AppPrefs.getInstance(context).getAccess(); }

    public String getRefresh() { return AppPrefs.getInstance(context).getRefresh(); }




    private List<TakeQuizQuestions> quizQuestionList;
    public RecyclerViewAdapterQuizQuestionList(List<TakeQuizQuestions> quizQuestionList) { this.quizQuestionList = quizQuestionList; }

    @NonNull
    @Override
    public RecyclerViewAdapterQuizQuestionList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
//        submitQuiz = rootView.findViewById(R.id.btn_quiz_submit);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterQuizQuestionList.ViewHolder holder, int position) {
        //int quizPk = quizQuestionList.get(position).getPk();
        String quizQuestion = quizQuestionList.get(position).getQuestion();
        //String[] quizOptions = quizQuestionList.get(position).getOptions();
        //String taskType = quizQuestionList.get(position).getPost_type();
        int questionPk = quizQuestionList.get(position).getPk();

        ArrayList<QuizAnswer> quizAns = new ArrayList<>();
        EditText quiz_ans = holder.quiz_ans;
        quiz_ans.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isOnTextChanged = true;

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(isOnTextChanged) {
                    isOnTextChanged = false;

                    try {
                        for(int i=0; i<= questionPk; i++) {
                            if(i != questionPk) {
                                quizAnswer.add("0");
                            } else {
                                quizAnswer.add("0");
                                quizAnswer.set(questionPk, s.toString());
                            }
                        }
                        Log.d("quizAnswer", quizAnswer.toString());

                    } catch (NumberFormatException e) {
                        for (int i=0; i<= questionPk; i++) {
                            Log.d("Item removed", " : " + i);
                            if(i == questionPk) {
                                quizAnswer.set(questionPk,"0");
                            }
                        }
                        Log.d("quizAnswer", quizAnswer.toString());
                    }
                }

            }
        });

       /* submitQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> removeQuizAns = new ArrayList<>();
                if(!quizAnswer.isEmpty()) {
                    for(int i=0; i<quizAnswer.size(); i++) {
                        if(!quizAnswer.get(i).equals("0")) {
                            removeQuizAns.add(quizAnswer.get(i));
                        }
                    }
                }
                Log.d("quizAnswer", removeQuizAns.toString());
            }
        });*/



        holder.setData(quizQuestion,quizQuestionList);

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
                int questionPk = quizQuestionList.get(holder.getAdapterPosition()).getPk();
                ArrayList<String> quizAns = quizAnswer;
                Log.d("Question pk on sumbit quiz recyclerview", String.valueOf(questionPk));
                Log.d("Question ans array on sumbit quiz recyclerview", String.valueOf(quizAns));
                Intent attendQuiz = new Intent(v.getContext(), ActivityTakeQuiz.class);
                attendQuiz.putExtra("questionPk", questionPk);
                attendQuiz.putExtra("quizAns", quizAns);
            }
        });

    }


    @Override
    public int getItemCount() {
        return quizQuestionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        EditText quiz_ans;
        TextView quiz_question, quiz_option1, quiz_option2, quiz_option3, quiz_option4;
        ConstraintLayout cardViewConstraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           quiz_question = itemView.findViewById(R.id.quiz_question);
           quiz_option1 = itemView.findViewById(R.id.quiz_option1);
           quiz_option2 = itemView.findViewById(R.id.quiz_option2);
           quiz_option3 = itemView.findViewById(R.id.quiz_option3);
           quiz_option4 = itemView.findViewById(R.id.quiz_option4);
           quiz_ans = itemView.findViewById(R.id.quiz_ans);
           cardViewConstraintLayout = itemView.findViewById(R.id.layout_post_comment);


        }

        /*public void QuizAnswer(){
            ArrayList<QuizAnswer> quizAnswer = new ArrayList<>();
            Log.d("Edit text value", String.valueOf(quiz_ans));

            for(int i=0; i< quizQuestionList.size(); i++) {
                int questionPk = quizQuestionList.get(i).getPk();
                //int selectedAnswer =
                QuizAnswer answer = new QuizAnswer(questionPk,2);
                //quizAnswer.add(1,QuizAnswer(1,2));
            }
        }*/

        public void setData(String quizQuestion, List<TakeQuizQuestions> quizQuestionList) {


            for (int i=0; i<quizQuestionList.size(); i++) {
                //Log.d("Quiz Question", quizQuestionList.get(i).getQuestion());
                //quiz_question.setText(quizQuestionList.get(i).getQuestion());
                quiz_question.setText(quizQuestion);
                Log.d("Quiz Option", quizQuestionList.get(i).getOptions()[i]);
                quiz_option1.setText(quizQuestionList.get(i).getOptions()[0]);
                quiz_option2.setText(quizQuestionList.get(i).getOptions()[1]);
                quiz_option3.setText(quizQuestionList.get(i).getOptions()[2]);
                quiz_option4.setText(quizQuestionList.get(i).getOptions()[3]);

            }
        }
    }
}
