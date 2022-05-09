package com.rifatul.trackroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.rifatul.trackroom.models.TakeQuizDetails;
import com.rifatul.trackroom.models.User;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDetailedQuizPrivate extends BaseDataActivity {
    TextView et_quiz_title, et_quiz_deadline, et_quiz_description, et_quiz_start_time, et_quiz_end_time, tv_quiz_start_time, tv_quiz_end_time;
    CircleImageView profileImage;
    AppCompatButton btn_take_quiz, btn_view_results;
    String start_time = "24-04-2022 00:00";
    String end_time = "24-04-2022 00:30";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_quiz_private);

        et_quiz_title = findViewById(R.id.et_quiz_title);
        et_quiz_deadline = findViewById(R.id.et_quiz_deadline);
        et_quiz_description = findViewById(R.id.et_quiz_description);
        profileImage = findViewById(R.id.img_Profile_Photo_mini);
        btn_take_quiz = findViewById(R.id.btn_take_quiz);
        et_quiz_start_time = findViewById(R.id.et_quiz_start_time);
        et_quiz_end_time = findViewById(R.id.et_quiz_end_time);
        tv_quiz_start_time = findViewById(R.id.tv_quiz_start_time);
        tv_quiz_end_time = findViewById(R.id.tv_quiz_end_time);
        btn_view_results = findViewById(R.id.btn_view_results);


        Intent PostInfo = getIntent();
        int postPK = PostInfo.getIntExtra("postPk", 0);
        String postTitle = PostInfo.getStringExtra("postTitle");
        String postDate = PostInfo.getStringExtra("postDate");
        String postDescription = PostInfo.getStringExtra("postDescription");
        String postCreatorImage = PostInfo.getStringExtra("postCreatorImage");


        displayPostInfo(postTitle, postDate, postDescription, postPK, postCreatorImage);

        btn_take_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takeQuiz = new Intent(getApplicationContext(), ActivityTakeQuiz.class);
                takeQuiz.putExtra("quizPk", postPK);
                takeQuiz.putExtra("quizTitle", postTitle);
                Log.d("Post pk: ", String.valueOf(postPK));
                Log.d("Post pk: ", postTitle);
                startActivity(takeQuiz);
            }
        });

        btn_view_results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewResults = new Intent(getApplicationContext(), ActivityViewResultPrivate.class);
                viewResults.putExtra("quizPk", postPK);
                viewResults.putExtra("quizTitle", postTitle);
                Log.d("Post pk: ", String.valueOf(postPK));
                Log.d("Post pk: ", postTitle);
                startActivity(viewResults);
            }
        });

    }



    private void displayPostInfo(String postTitle, String postDate, String postDescription, int postPk, String postCreatorImage) {

        /*TakeQuizDetails takeQuizDetail = new TakeQuizDetails(postPk, postTitle, postDate, postDescription, start_time, end_time);

        Call<ResponseBody> takeQuizDetails = getApi().takeQuizDetails(getAccess(), postPk, takeQuizDetail);
        takeQuizDetails.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("Response", String.valueOf(response.code()));
                //Log.d("Post file:", postFile.getFile());
                if (response.isSuccessful()) {
                    et_quiz_title.setText(takeQuizDetail.getTitle());
                    et_quiz_deadline.setText(takeQuizDetail.getDate_created());
                    et_quiz_description.setText(takeQuizDetail.getDescription());
                    et_quiz_start_time.setText(takeQuizDetail.getStart_time());
                    et_quiz_end_time.setText(takeQuizDetail.getEnd_time());

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Log.d("Material Link on failure", t.toString());
                Toast.makeText(getApplicationContext(), "Server Not Found", Toast.LENGTH_SHORT).show();

            }
        });*/
        et_quiz_title.setText(postTitle);
        et_quiz_deadline.setText(postDate);
        et_quiz_description.setText(postDescription);
        tv_quiz_start_time.setText(start_time);
        tv_quiz_end_time.setText(end_time);
        Glide.with(getApplicationContext()).load(postCreatorImage).into(profileImage);

        //getAccountInfo();
    }


    /*private void getAccountInfo() {
        Call<User> getUserInfo = getApi().account(getAccess());

        getUserInfo.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User u = response.body();
                    if (u.getProfileImage() != null)
                        displayProfilePicture(u.getProfileImage());
                }
                else {
                    Toast.makeText(getApplicationContext(), "Unable To Get Profile Information.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Function getUserInfo", "Failure");
                Log.d("Function getUserInfo Throwable T", t.toString());
            }
        });

    }

    private void displayProfilePicture(String url) {
        Log.d("Function displayProfilePicture name", url);
        Glide.with(getApplicationContext()).load(url).into(profileImage);
    }
    /*@Override
    public void onBackPressed() {
        Intent back = new Intent(getApplicationContext(), ActivityTrackroom.class);
        startActivity(back);
    }*/
}
