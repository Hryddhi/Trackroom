package com.rifatul.trackroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.rifatul.trackroom.models.QuizStatsStudent;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityViewResultPrivate extends BaseDataActivity{
    TextView grade;
    boolean hasAttended = true;
    String quizGrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results_private);
        grade = findViewById(R.id.grade);

        Intent PostInfo = getIntent();
        int postPK = PostInfo.getIntExtra("quizPk", 0);
        String postTitle = PostInfo.getStringExtra("quizTitle");


        getQuizGrade(postPK);
    }

    private void getQuizGrade(int postPK) {
        Call<QuizStatsStudent> getStudentQuizStats = getApi().getStudentQuizStats(getAccess(), postPK);
        getStudentQuizStats.enqueue(new Callback<QuizStatsStudent>() {
            @Override
            public void onResponse(Call<QuizStatsStudent> call, Response<QuizStatsStudent> response) {
                //Log.d("Quiz Content:", String.valueOf(quizContent));
                //Log.d("Quiz Data:", String.valueOf(quizData.getTitle(), quizData.getDescription(), quizData.getStartTime(), quizData.getEndTime());

                if (response.isSuccessful()) {
                    QuizStatsStudent quizStats = response.body();
                    quizGrade = quizStats.getGrade();
                    grade.setText(quizGrade);


                }
            }

            @Override
            public void onFailure(Call<QuizStatsStudent> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }


}
