package com.rifatul.trackroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rifatul.trackroom.adapters.RecyclerViewAdapterQuizStatsList;
import com.rifatul.trackroom.models.QuizStatsTeacher;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityViewQuizStatsCreated extends BaseDataActivity{

    RecyclerView recyclerViewQuizStats;
    RecyclerViewAdapterQuizStatsList recyclerViewAdapterQuizStatsList;
    List<QuizStatsTeacher> itemQuizStatsList;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_stats_created);

        //grade = findViewById(R.id.grade);

        Intent PostInfo = getIntent();
        int postPK = PostInfo.getIntExtra("quizPk", 0);

        initRecyclerViewData(postPK);

        /*Intent quizResult = new Intent(getApplicationContext(), ActivityViewResultCreated.class);
        quizResult.putExtra("quizStatsPk", postPK);
        Log.d("Post pk: ", String.valueOf(postPK));
        startActivity(quizResult);*/




    }

    private void initRecyclerViewData(int postPK) {
        itemQuizStatsList = new ArrayList<>();



        Log.d("Bearer Access on Fragment Class List", getAccess());


        Call<List<QuizStatsTeacher>> getTeacherQuizStatsList = getApi().getTeacherQuizStatsList(getAccess(), postPK);

        getTeacherQuizStatsList.enqueue(new Callback<List<QuizStatsTeacher>>() {
            @Override
            public void onResponse(Call<List<QuizStatsTeacher>> call, Response<List<QuizStatsTeacher>> response) {
                Log.d("TAG", "Response " + response.code());

                if (response.isSuccessful()) {
                    List<QuizStatsTeacher> data = response.body();
                    for (QuizStatsTeacher itemQuizStats : data) {
                        itemQuizStatsList.add(itemQuizStats);
                    }
                    initRecyclerView();
                }
                else
                    Toast.makeText(getApplicationContext(), "Failed To Receive Post List", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<QuizStatsTeacher>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Server Not Found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecyclerView() {
        recyclerViewQuizStats = findViewById(R.id.quiz_stats_recycler_attend_question);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewQuizStats.setLayoutManager(layoutManager);
        recyclerViewAdapterQuizStatsList = new RecyclerViewAdapterQuizStatsList(itemQuizStatsList);
        recyclerViewQuizStats.setAdapter(recyclerViewAdapterQuizStatsList);
        recyclerViewAdapterQuizStatsList.notifyDataSetChanged();
    }


}
