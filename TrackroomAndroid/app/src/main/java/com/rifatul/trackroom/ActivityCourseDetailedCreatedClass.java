package com.rifatul.trackroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rifatul.trackroom.adapters.RecyclerViewAdapterAssignmemtListCreated;
import com.rifatul.trackroom.models.ItemAssignments;
import com.rifatul.trackroom.models.User;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCourseDetailedCreatedClass extends BaseDataActivity{
    TextView txt_name;
    TextView post_rating;
    TextView post_category;
    TextView post_description;
    CardView postCardLayout;
    ConstraintLayout postConstraint;
    AppCompatButton btnInviteStudent;
    CircleImageView profileImage;
    RecyclerView recyclerView;
    RecyclerViewAdapterAssignmemtListCreated recyclerViewAdapterAssignmentList;
    List<ItemAssignments> itemAssignmentsList;
    RecyclerView.LayoutManager layoutManager;
    String rating = "No Rating Yet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detailed_created_class);

        txt_name = findViewById(R.id.txt_Name);
        post_rating = findViewById(R.id.item_post_rating);
        post_category = findViewById(R.id.item_post_category);
        post_description = findViewById(R.id.post_description);
        postCardLayout = findViewById(R.id.layout_post_Card);
        postConstraint = findViewById(R.id.constraint_layout_container);
        profileImage = findViewById(R.id.img_Profile_Photo_mini);
        btnInviteStudent = findViewById(R.id.btn_invite_students);

        //btn_leave = findViewById(R.id.btn_leave);
//        getClassList();



        Intent ClassroomInfo = getIntent();
        int classPK = ClassroomInfo.getIntExtra("classPk", 0);
        String classTitle = ClassroomInfo.getStringExtra("classTitle");
        String classRating = ClassroomInfo.getStringExtra("classRating");
        String classCategory = ClassroomInfo.getStringExtra("classCategory");
        String classDescription = ClassroomInfo.getStringExtra("classDescription");
        String classType = ClassroomInfo.getStringExtra("classType");

        displayInfo(classTitle, classRating, classCategory, classDescription);

        initRecyclerViewData(classPK);

        postConstraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(classType.equals("Private")) {
                    Intent uploadMaterial = new Intent(getApplicationContext(), ActivityPost.class);
                    uploadMaterial.putExtra("uploadMaterialClassroomPk", classPK);
                    Log.d("Class pk: ", String.valueOf(classPK));
                    startActivity(uploadMaterial);
                } else {
                    Intent uploadMaterialPublic = new Intent(getApplicationContext(), ActivityPostPublic.class);
                    uploadMaterialPublic.putExtra("uploadMaterialClassroomPk", classPK);
                    Log.d("Class pk: ", String.valueOf(classPK));
                    startActivity(uploadMaterialPublic);
                }
            }
        });

        btnInviteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inviteStudents = new Intent(getApplicationContext(), ActivityInviteStudent.class);
                //inviteStudents.putExtra("InviteClassCode", classCode);
                inviteStudents.putExtra("InviteClassPK", classPK);
                startActivity(inviteStudents);
            }
        });
    }

    private void displayInfo(String classTitle, String classRating, String classCategory, String classDescription) {
        txt_name.setText(classTitle);
        post_rating.setText(classRating);
        post_category.setText(classCategory);
        post_description.setText(classDescription);
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

    private void initRecyclerViewData(int classPK) {
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
        recyclerViewAdapterAssignmentList = new RecyclerViewAdapterAssignmemtListCreated(itemAssignmentsList);
        recyclerView.setAdapter(recyclerViewAdapterAssignmentList);
        recyclerViewAdapterAssignmentList.notifyDataSetChanged();
    }

    private void displayProfilePicture (String url) {
        Log.d("Function displayProfilePicture name", url);
        Glide.with(getApplicationContext()).load(url).into(profileImage);
    }

    @Override
    public void onBackPressed() {
        Intent back = new Intent(getApplicationContext(), ActivityTrackroom.class);
        startActivity(back);
    }
}
