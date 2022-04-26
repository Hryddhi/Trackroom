package com.rifatul.trackroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.rifatul.trackroom.adapters.RecyclerViewAdapterQuizQuestionList;
import com.rifatul.trackroom.adapters.RecyclerViewAdapterQuizStatsList;
import com.rifatul.trackroom.models.QuizStatsStudent;
import com.rifatul.trackroom.models.QuizStatsTeacher;
import com.rifatul.trackroom.models.TakeQuizQuestions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityViewResultCreated extends BaseDataActivity{

    TextView grade_create;
    boolean hasAttended = true;
    String quizGrade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results_created);

        grade_create = findViewById(R.id.grade_create);

        Intent PostInfo = getIntent();
        //int postPK = PostInfo.getIntExtra("quizStatsPk", 0);
        //int postPK = PostInfo.getIntExtra("quizPk", 0);
        String userName = PostInfo.getStringExtra("userName");
        boolean hasAttended = PostInfo.getBooleanExtra("hasAttended", true);
        String grade = PostInfo.getStringExtra("grade");

        //getQuizGrade(postPK);
        getQuizGrade(grade);

    }

    private void getQuizGrade(String grade) {
        grade_create.setText(grade);
    }

    /*private void getQuizGrade(int postPK) {
        Call<QuizStatsTeacher> getTeacherQuizStats = getApi().getTeacherQuizGrade(getAccess(), postPK);
        getTeacherQuizStats.enqueue(new Callback<QuizStatsTeacher>() {
            @Override
            public void onResponse(Call<QuizStatsTeacher> call, Response<QuizStatsTeacher> response) {
                //Log.d("Quiz Content:", String.valueOf(quizContent));
                //Log.d("Quiz Data:", String.valueOf(quizData.getTitle(), quizData.getDescription(), quizData.getStartTime(), quizData.getEndTime());

                if (response.isSuccessful()) {
                    QuizStatsTeacher quizStats = response.body();
                    //quizGrade = quizStats.getGrade();
                    grade_create.setText(quizStats.getGrade());


                }
            }

            @Override
            public void onFailure(Call<QuizStatsTeacher> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }*/
}
