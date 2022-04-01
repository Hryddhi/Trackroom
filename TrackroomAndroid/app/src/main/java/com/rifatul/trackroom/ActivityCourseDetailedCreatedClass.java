package com.rifatul.trackroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rifatul.trackroom.adapters.RecyclerViewAdapterAssignmemtList;
import com.rifatul.trackroom.models.ItemAssignments;
import com.rifatul.trackroom.models.ItemClass;
import com.rifatul.trackroom.models.User;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCourseDetailedCreatedClass extends BaseDataActivity{
    CircleImageView img_profile_photo;
    TextView txt_name;
    TextView post_txt;
    //AppCompatButton btn_leave;
    List<ItemClass> classList ;
    RecyclerView recyclerView;
    RecyclerViewAdapterAssignmemtList recyclerViewAdapterAssignmentList;
    List<ItemAssignments> itemAssignmentsList;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detailed_created_class);

        txt_name = findViewById(R.id.txt_Name);
        post_txt = findViewById(R.id.post_text);
        img_profile_photo = findViewById(R.id.img_profile_photo);
        //btn_leave = findViewById(R.id.btn_leave);
//        getClassList();

        post_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPost();
            }
        });

        Intent ClassroomInfo = getIntent();
        int classPK = ClassroomInfo.getIntExtra("classPk", 0);

        initRecyclerViewData(classPK);

       // Intent ClassroomInfo = getIntent();
        //int classPK = ClassroomInfo.getIntExtra("classPk", 0);
        //String classroomName = ClassroomInfo.getStringExtra("classroomName");

        //displayInfo(classroomName);
        //getClassList(classPK);



    /*private void displayInfo(String classroomName) {

        //txt_name.setText(classroomName);
        //getClassList();
        Log.d("Function displayActionBarInfo name", classroomName);
        txt_name = findViewById(R.id.txt_Name);
        txt_name.setText(classroomName);
    }

    private void getClassList( int classPk) {
        classList = new ArrayList<>();
//        Intent ClassroomInfo = getIntent();

//        int classroomPk = ClassroomInfo.getIntExtra("classroomPk", 0);
//        Log.d("Bearer Access on Fragment Class List", getAccess());

        Call<List<ItemClass>> getClassroomList = getApi().getClassroomList(getAccess());

        getClassroomList.enqueue(new Callback<List<ItemClass>>() {
            @Override
            public void onResponse(Call<List<ItemClass>> call, Response<List<ItemClass>> response) {



                if (response.isSuccessful()) {
                    List<ItemClass> list = response.body();
                    Log.d("TAG", "Response " + response.code());

                }
                else
                    Toast.makeText(getApplicationContext(), "Failed To Receive Class List", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<ItemClass>> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.toString());
                Toast.makeText(getApplicationContext(), "Server Not Found", Toast.LENGTH_SHORT).show();
            }
        });
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
        Glide.with(getApplicationContext()).load(url).into(img_profile_photo);
    }

    */
    }

    private void initRecyclerViewData(int classPK) {
        itemAssignmentsList = new ArrayList<>();
        //itemAssignmentsList.add(new ItemAssignments(1,"reading material", "Title", "Description", "12/12/21", "11/11/21", "class"));



        Log.d("Bearer Access on Fragment Class List", getAccess());

        initRecyclerView();

        //Call<List<ItemAssignments>> getAssignmentList = getApi().getAssignmentList(getAccess(),classPK);

        /*getAssignmentList.enqueue(new Callback<List<ItemAssignments>>() {
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
                    Toast.makeText(getApplicationContext(), "Failed To Receive Assignment List", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<ItemAssignments>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Server Not Found", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.rv_assignment_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapterAssignmentList = new RecyclerViewAdapterAssignmemtList(itemAssignmentsList);
        recyclerView.setAdapter(recyclerViewAdapterAssignmentList);
        recyclerViewAdapterAssignmentList.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        Intent back = new Intent(getApplicationContext(), ActivityTrackroom.class);
        startActivity(back);
    }
}
