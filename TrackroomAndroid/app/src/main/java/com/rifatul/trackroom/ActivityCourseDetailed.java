package com.rifatul.trackroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rifatul.trackroom.adapters.RecyclerViewAdapterAssignmemtListPrivate;
import com.rifatul.trackroom.models.ItemAssignments;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCourseDetailed extends BaseDataActivity {
    TextView post_rating;
    TextView rating_tv;
    TextView txt_Name;
    TextView post_category;
    TextView post_description;
    CardView postCardLayout;
    AppCompatButton btn_leave, btn_rate;
    RatingBar rating_bar;

    RecyclerView recyclerView;
    RecyclerViewAdapterAssignmemtListPrivate recyclerViewAdapterAssignmentList;
    List<ItemAssignments> itemAssignmentsList;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detailed);

        txt_Name = findViewById(R.id.txt_Name);
        post_rating = findViewById(R.id.item_post_rating);
        rating_tv = findViewById(R.id.item_recom_tv_rating);
        post_category = findViewById(R.id.item_post_category);
        post_description = findViewById(R.id.post_description);
        postCardLayout = findViewById(R.id.layout_post_Card);

        btn_rate =findViewById(R.id.btn_rating);
        rating_bar = findViewById(R.id.rating_bar);

        btn_leave = findViewById(R.id.btn_leave);


        btn_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = String.valueOf(rating_bar.getRating());
            }
        });





        Intent ClassroomInfo = getIntent();
        int classPK = ClassroomInfo.getIntExtra("classPk", 0);
        String classTitle = ClassroomInfo.getStringExtra("classTitle");
        String classRating = ClassroomInfo.getStringExtra("classRating");
        String classCategory = ClassroomInfo.getStringExtra("classCategory");
        String classDescription = ClassroomInfo.getStringExtra("classDescription");


        displayInfo(classTitle, classRating, classCategory, classDescription);
        initRecyclerViewData(classPK);


        btn_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent ClassroomInfo = getIntent();
                int classPK = ClassroomInfo.getIntExtra("classPk", 0);*/
                /*String classTitle = ClassroomInfo.getStringExtra("classTitle");
                String classRating = ClassroomInfo.getStringExtra("classRating");
                String classCategory = ClassroomInfo.getStringExtra("classCategory");*/

               /* Log.d("Classroom title on detailed classroom view : ", classTitle );
                Log.d("Classroom rating on detailed classroom view : ", classRating);
                Log.d("Classroom category on detailed classroom view : ", classCategory);*/

               Call<ResponseBody> leaveClass = getApi().leaveClass(getAccess(), classPK);

                leaveClass.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                        if (response.isSuccessful()) {
                            /*Intent startTrackroom = new Intent(getApplicationContext(), ActivityTrackroom.class);
                           getApplicationContext().startActivity(startTrackroom);*/
                            Log.d("Classroom pk on detailed classroom view : ", String.valueOf(classPK));
                            Log.d("TAG", "Response " + response.code());
                            startTrackroom();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("TAG", "onFailure: " + t.toString());
                        Toast.makeText(getApplicationContext(), "Server Not Found", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    private void displayInfo(String classTitle, String classRating, String classCategory, String classDescription) {
        txt_Name.setText(classTitle);
        post_rating.setText(classRating);
        post_category.setText(classCategory);
        post_description.setText(classDescription);
    }


    private void initRecyclerViewData ( int classPK) {
            itemAssignmentsList = new ArrayList<>();

            Log.d("Bearer Access on Fragment Class List", getAccess());


            Call<List<ItemAssignments>> getPostList = getApi().getPostList(getAccess(),classPK);

        getPostList.enqueue(new Callback<List<ItemAssignments>>() {
            @Override
            public void onResponse(Call<List<ItemAssignments>> call, Response<List<ItemAssignments>> response) {
                Log.d("TAG", "Response " + response.code());

                if (response.isSuccessful()) {
                    List<ItemAssignments> data = response.body();
                    for (ItemAssignments itemAssignment : data) {
                        itemAssignmentsList.add(itemAssignment);
                    }
                    initRecyclerView();
                }
                else
                    Toast.makeText(getApplicationContext(), "Failed To Receive Post List", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<ItemAssignments>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Server Not Found", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initRecyclerView() {
        recyclerView = findViewById(R.id.rv_assignment_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapterAssignmentList = new RecyclerViewAdapterAssignmemtListPrivate(itemAssignmentsList);
        recyclerView.setAdapter(recyclerViewAdapterAssignmentList);
        recyclerViewAdapterAssignmentList.notifyDataSetChanged();
    }







    @Override
    public void onBackPressed() {
        Intent back = new Intent(getApplicationContext(), ActivityTrackroom.class);
        startActivity(back);
    }
}


