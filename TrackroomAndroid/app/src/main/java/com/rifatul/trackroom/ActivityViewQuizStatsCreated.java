package com.rifatul.trackroom;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.rifatul.trackroom.adapters.RecyclerViewAdapterQuizQuestionList;
import com.rifatul.trackroom.adapters.RecyclerViewAdapterQuizStatsList;
import com.rifatul.trackroom.models.QuizStatsTeacher;
import com.rifatul.trackroom.models.TakeQuizQuestions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityViewQuizStatsCreated extends BaseDataActivity{

    TextView grade;
    boolean hasAttended = true;
    String quizGrade;

    RecyclerView recyclerView;
    RecyclerViewAdapterQuizStatsList recyclerViewAdapterQuizStatsList;
    List<QuizStatsTeacher> itemQuizStatsList;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_stats_created);

        //grade = findViewById(R.id.grade);

        Intent PostInfo = getIntent();
        //int postPK = PostInfo.getIntExtra("quizPk", 0);
        String userName = PostInfo.getStringExtra("userName");
        boolean hasAttended = PostInfo.getBooleanExtra("hasAttended", true);
        String grade = PostInfo.getStringExtra("grade");




    }

}
