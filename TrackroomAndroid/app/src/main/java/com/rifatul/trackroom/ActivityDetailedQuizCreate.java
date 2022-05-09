package com.rifatul.trackroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.rifatul.trackroom.models.User;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDetailedQuizCreate extends BaseDataActivity {
    TextView et_quiz_title, et_quiz_deadline, et_quiz_description, et_quiz_start_time, et_quiz_end_time, tv_quiz_start_time, tv_quiz_end_time;
    CircleImageView profileImage;
    AppCompatButton btn_quiz_results;
    String postFile, postFileType;
    String start_time = "24-04-2022 00:00";
    String end_time = "24-04-2022 00:30";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_quiz_create);

        et_quiz_title = findViewById(R.id.et_quiz_title);
        et_quiz_deadline = findViewById(R.id.et_quiz_deadline);
        et_quiz_description = findViewById(R.id.et_quiz_description);
        profileImage = findViewById(R.id.img_Profile_Photo_mini);
        et_quiz_start_time = findViewById(R.id.et_quiz_start_time);
        et_quiz_end_time = findViewById(R.id.et_quiz_end_time);
        tv_quiz_start_time = findViewById(R.id.tv_quiz_start_time);
        tv_quiz_end_time = findViewById(R.id.tv_quiz_end_time);
        btn_quiz_results = findViewById(R.id.btn_quiz_results);


        Intent PostInfo = getIntent();
        int postPK = PostInfo.getIntExtra("postPk", 0);
        String postTitle = PostInfo.getStringExtra("postTitle");
        String postDate = PostInfo.getStringExtra("postDate");
        String postDescription = PostInfo.getStringExtra("postDescription");
        String postCreatorImage = PostInfo.getStringExtra("postCreatorImage");


        displayPostInfo(postTitle, postDate, postDescription, postPK, postCreatorImage);

        btn_quiz_results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent quizResults = new Intent(getApplicationContext(), ActivityViewQuizStatsCreated.class);
                quizResults.putExtra("quizPk", postPK);
                Log.d("Post pk: ", String.valueOf(postPK));
                startActivity(quizResults);
            }
        });

    }



    private void displayPostInfo(String postTitle, String postDate, String postDescription, int postPk, String postCreatorImage) {

        /*Call<PostFile> getPostDetails = getApi().getPostDetails(getAccess(), postPk);
        getPostDetails.enqueue(new Callback<PostFile>() {
            @Override
            public void onResponse(Call<PostFile> call, Response<PostFile> response) {
                Log.d("Response", String.valueOf(response.code()));
                //Log.d("Post file:", postFile.getFile());
                if (response.isSuccessful()) {
                    PostFile post = response.body();
                    Log.d("Response Success", "success");
                    postFileType = post.getFile_type();
                    postFile = post.getFile();
                    Log.d("Post file:", post.getFile());
                    Log.d("Post file:", post.getFile_type());

                }
            }

            @Override
            public void onFailure(Call<PostFile> call, Throwable t) {
                //Log.d("Material Link on failure", t.toString());
                Toast.makeText(getApplicationContext(), "Server Not Found", Toast.LENGTH_SHORT).show();

            }
        });*/
        et_quiz_title.setText(postTitle);
        et_quiz_deadline.setText(postDate);
        et_quiz_description.setText(postDescription);
        tv_quiz_start_time.setText(start_time);
        tv_quiz_end_time.setText(end_time);

        getAccountInfo();
    }


    private void getAccountInfo() {
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
