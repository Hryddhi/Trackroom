package com.rifatul.trackroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rifatul.trackroom.adapters.RecyclerViewAdapterAssignmemtListCreated;
import com.rifatul.trackroom.models.ItemAssignments;
import com.rifatul.trackroom.models.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDetailedPostCreate  extends BaseDataActivity {
    TextView postTitle, postDate, postDescription;
    CircleImageView profileImage;
    RecyclerView recyclerView;
    RecyclerViewAdapterAssignmemtListCreated recyclerViewAdapterAssignmentList;
    List<ItemAssignments> itemAssignmentsList;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_post_create);

        postTitle = findViewById(R.id.post_title);
        postDate = findViewById(R.id.post_deadline);
        postDescription = findViewById(R.id.post_description);

        Intent ClassroomInfo = getIntent();
        int postPk = ClassroomInfo.getIntExtra("postPk", 0);
        String postTitle = ClassroomInfo.getStringExtra("postTitle");
        String postDate = ClassroomInfo.getStringExtra("postDate");
        String postDescription = ClassroomInfo.getStringExtra("postDescription");

        //displayInfo(postTitle, postDate, postDescription);
    }

    private void displayInfo(String postTitle, String postDate, String postDescription) {
        /*postTitle.setText(postTitle);
        postDate.setText(postDate);
        postDescription.setText(postDescription);*/
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

    @Override
    public void onBackPressed() {
        Intent back = new Intent(getApplicationContext(), ActivityTrackroom.class);
        startActivity(back);
    }
}
