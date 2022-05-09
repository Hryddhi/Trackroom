package com.rifatul.trackroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rifatul.trackroom.adapters.RecyclerViewAdapterCommentList;
import com.rifatul.trackroom.models.ItemComments;
import com.rifatul.trackroom.models.PostComment;
import com.rifatul.trackroom.models.PostFile;
import com.rifatul.trackroom.models.User;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDetailedPostCreate  extends BaseDataActivity {
    TextView et_post_title, et_post_deadline, et_post_description, et_post_filename;
    CircleImageView profileImage, profileImageComment;
    ImageView image_view;
    String postFile, postFileType;
    EditText et_comment;

    RecyclerView recyclerView;
    RecyclerViewAdapterCommentList recyclerViewAdapterCommentList;
    List<ItemComments> itemCommentsList;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_post_create);

        et_post_title = findViewById(R.id.et_post_title);
        et_post_deadline = findViewById(R.id.et_post_deadline);
        et_post_description = findViewById(R.id.et_post_description);
        et_post_filename = findViewById(R.id.et_post_filename);
        et_comment = findViewById(R.id.et_comment);
        profileImage = findViewById(R.id.img_Profile_Photo_mini);
        image_view = findViewById(R.id.image_View);
        profileImageComment = findViewById(R.id.img_Profile_Photo_Comment);


        Intent PostInfo = getIntent();
        int postPK = PostInfo.getIntExtra("postPk", 0);
        String postTitle = PostInfo.getStringExtra("postTitle");
        String postDate = PostInfo.getStringExtra("postDate");
        String postDescription = PostInfo.getStringExtra("postDescription");
        String postCreatorImage = PostInfo.getStringExtra("postCreatorImage");
       // String postFile = PostInfo.getStringExtra("postFile");


        displayPostInfo(postTitle, postDate, postDescription, postPK, postCreatorImage);
        initRecyclerViewData(postPK);


        et_post_filename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(postFileType.equals("PDF")) {
                    Intent postDetails = new Intent(getApplicationContext(), ActivityViewPostCreated.class);
                    postDetails.putExtra("postFile", postFile);
                    postDetails.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(postDetails);
                }
            }
        });

        et_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEditText(et_comment)) {
                    postComment(postPK);
                   //et_comment.getText().clear();
                }
            }
        });
    }

    private void postComment(int postPK) {
        String comment = et_comment.getText().toString();

        PostComment newComment = new PostComment(comment);
        Call<ResponseBody> postComment = getApi().postComment(getAccess(), postPK, newComment);

        postComment.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("Function comment post response code", String.valueOf(response.code()));
                    Toast.makeText(getApplicationContext(), "Comment posted Successfully", Toast.LENGTH_SHORT).show();
                    startTrackroom();
                }
                else
                    Toast.makeText(getApplicationContext(), "Comment not posted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("comment on failure", t.toString());
                Toast.makeText(getApplicationContext(), "Server Not Found", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void initRecyclerViewData(int postPK) {
        itemCommentsList = new ArrayList<>();



        Log.d("Bearer Access on Fragment Class List", getAccess());


        Call<List<ItemComments>> getCommentsList = getApi().getCommentsList(getAccess(),postPK);

        getCommentsList.enqueue(new Callback<List<ItemComments>>() {
            @Override
            public void onResponse(Call<List<ItemComments>> call, Response<List<ItemComments>> response) {
                Log.d("TAG", "Response " + response.code());

                if (response.isSuccessful()) {
                    List<ItemComments> data = response.body();
                    for (ItemComments itemComment : data) {
                        itemCommentsList.add(itemComment);
                    }
                    initRecyclerView();
                }
                else
                    Toast.makeText(getApplicationContext(), "Failed To Receive Post List", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<ItemComments>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Server Not Found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.post_comment_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapterCommentList = new RecyclerViewAdapterCommentList(itemCommentsList);
        recyclerView.setAdapter(recyclerViewAdapterCommentList);
        recyclerViewAdapterCommentList.notifyDataSetChanged();
    }


    private void displayPostInfo(String postTitle, String postDate, String postDescription, int postPk, String postCreatorImage) {
        //PostFile postFile = new PostFile(post_file, post_file_type);

        Call<PostFile> getPostDetails = getApi().getPostDetails(getAccess(), postPk);
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
                    if (!postFileType.equals("PDF")) {
                        image_view.setAlpha(1f);
                        Glide.with(getApplicationContext()).load(post.getFile()).into(image_view);


                    } else {
                        et_post_filename.setAlpha(1);
                        et_post_filename.setText(post.getFile());
                    }

                }
            }

            @Override
            public void onFailure(Call<PostFile> call, Throwable t) {
                //Log.d("Material Link on failure", t.toString());
                Toast.makeText(getApplicationContext(), "Server Not Found", Toast.LENGTH_SHORT).show();

            }
        });
        et_post_title.setText(postTitle);
        et_post_deadline.setText(postDate);
        et_post_description.setText(postDescription);
        /*Log.d("Creator image on detailed post view", postCreatorImage);
        Glide.with(getApplicationContext()).load(postCreatorImage).into(profileImage);*/

        //et_post_filename.setText(link);



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
    /*@Override
    public void onBackPressed() {
        Intent back = new Intent(getApplicationContext(), ActivityTrackroom.class);
        startActivity(back);
    }*/
}
