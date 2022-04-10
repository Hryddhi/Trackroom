package com.rifatul.trackroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rifatul.trackroom.adapters.RecyclerViewAdapterAssignmemtList;
import com.rifatul.trackroom.adapters.RecyclerViewAdapterAssignmemtListCreated;
import com.rifatul.trackroom.adapters.RecyclerViewAdapterCommentList;
import com.rifatul.trackroom.models.ItemAssignments;
import com.rifatul.trackroom.models.ItemComments;
import com.rifatul.trackroom.models.User;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDetailedPost extends BaseDataActivity {
    TextView et_post_title, et_post_deadline, et_post_description, et_post_filename;
    CircleImageView profileImage, profileImageComment;

    RecyclerView recyclerView;
    RecyclerViewAdapterAssignmemtList recyclerViewAdapterCommentList;
    List<ItemAssignments> itemCommentsList;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_post);

        et_post_title = findViewById(R.id.et_post_title);
        et_post_deadline = findViewById(R.id.et_post_deadline);
        et_post_description = findViewById(R.id.et_post_description);
        et_post_filename = findViewById(R.id.et_post_filename);
        profileImage = findViewById(R.id.img_Profile_Photo_mini);
        profileImageComment = findViewById(R.id.img_Profile_Photo_Comment);


        Intent PostInfo = getIntent();
        int postPK = PostInfo.getIntExtra("postPk", 0);
        String postTitle = PostInfo.getStringExtra("postTitle");
        String postDate = PostInfo.getStringExtra("postDate");
        String postDescription = PostInfo.getStringExtra("postDescription");

        displayPostInfo(postTitle, postDate, postDescription);
        initRecyclerViewData(postPK);




    }

    private void initRecyclerViewData(int postPK) {
        itemCommentsList = new ArrayList<>();

        /*List<ItemAssignments> data ;

        for (ItemComments itemComment : data) {
            itemCommentsListList.add(itemComment);
        }*/
        //initRecyclerView();



        Log.d("Bearer Access on Fragment Class List", getAccess());


        /*Call<List<ItemAssignments>> getPostDetails = getApi().getPostDetails(getAccess(),postPK);

        getPostDetails.enqueue(new Callback<List<ItemAssignments>>() {
            @Override
            public void onResponse(Call<List<ItemAssignments>> call, Response<List<ItemAssignments>> response) {
                Log.d("TAG", "Response " + response.code());

                if (response.isSuccessful()) {
                    List<ItemAssignments> data = response.body();
                    for (ItemAssignments itemAssignment : data) {
                        itemCommentsList.add(itemAssignment);
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
        });*/
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.post_comment_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapterCommentList = new RecyclerViewAdapterAssignmemtList(itemCommentsList);
        recyclerView.setAdapter(recyclerViewAdapterCommentList);
        recyclerViewAdapterCommentList.notifyDataSetChanged();
    }


    private void displayPostInfo(String postTitle, String postDate, String postDescription) {
        et_post_title.setText(postTitle);
        et_post_deadline.setText(postDate);
        et_post_description.setText(postDescription);
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
        Glide.with(getApplicationContext()).load(url).into(profileImageComment);
    }
    @Override
    public void onBackPressed() {
        Intent back = new Intent(getApplicationContext(), ActivityTrackroom.class);
        startActivity(back);
    }
}
