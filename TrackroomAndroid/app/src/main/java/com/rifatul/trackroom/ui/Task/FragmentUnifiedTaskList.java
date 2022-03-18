package com.rifatul.trackroom.ui.Task;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rifatul.trackroom.R;
//import com.rifatul.trackroom.adapters.RecyclerViewAdapterUnifiedAssignmentList;
import com.rifatul.trackroom.models.ItemAssignments;
import com.rifatul.trackroom.models.User;
import com.rifatul.trackroom.ui.BaseDataFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentUnifiedTaskList extends BaseDataFragment {

    Context context;
    RecyclerView recyclerViewStudent;
    //RecyclerView recyclerViewTeacher;

    //List<ItemAssignments> assignmentList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unified_list, container, false);

        recyclerViewStudent = view.findViewById(R.id.rv_unified_assignment);
        //recyclerViewTeacher = view.findViewById(R.id.rv_unified_questions);


        getAccountInfo();

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAccountInfo();
    }

    //private void initRecyclerViewDataTeachers() {
        /*Log.d("Bearer Access on Fragment Class List", getAccess());
        Call<List<ItemAssignments>> getUnifiedAssignments = getApi().getUnifiedAssignments(getAccess());

        getUnifiedAssignments.enqueue(new Callback<List<ItemAssignments>>() {
            @Override
            public void onResponse(Call<List<ItemAssignments>> call, Response<List<ItemAssignments>> response) {
                Log.d("TAG", "Response " + response.code());

                if (response.isSuccessful()) {
                    List<ItemAssignments> data = response.body();
                    for (ItemAssignments itemAssignments : data) {
                        assignmentList.add(itemAssignments);
                    }
                    addDataToRecyclerView(recyclerViewStudent, assignmentList);
                }
                else
                    Toast.makeText(getContext(), "Failed To Receive Class List", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<ItemAssignments>> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.toString());
                Toast.makeText(getContext(), "Server Not Found", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    /*private void initRecyclerViewDataStudent() {
        Log.d("Bearer Access on Fragment Class List", getAccess());
        Call<List<ItemAssignments>> getUnifiedAssignments = getApi().getUnifiedAssignments(getAccess());

        getUnifiedAssignments.enqueue(new Callback<List<ItemAssignments>>() {
            @Override
            public void onResponse(Call<List<ItemAssignments>> call, Response<List<ItemAssignments>> response) {
                Log.d("TAG", "Response " + response.code());

                if (response.isSuccessful()) {
                    List<ItemAssignments> data = response.body();
                    for (ItemAssignments itemAssignments : data) {
                        assignmentList.add(itemAssignments);
                    }
                    addDataToRecyclerView(recyclerViewStudent, assignmentList);
                }
                else
                    Toast.makeText(getContext(), "Failed To Receive Class List", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<ItemAssignments>> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.toString());
                Toast.makeText(getContext(), "Server Not Found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addDataToRecyclerView (RecyclerView recyclerView, List<ItemAssignments> data) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RecyclerViewAdapterUnifiedAssignmentList(data));
    }*/

    private void getAccountInfo() {
        Call<User> getUserInfo = getApi().account(getAccess());
        getUserInfo.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User u = response.body();
                    /*if (u.getProfileType() == null) {
                        Toast.makeText(context, "Profile Type Not Set.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (u.getProfileType().equals("Student")){
                            //recyclerViewStudent.setAlpha(1);
                            initRecyclerViewDataStudent();
                        }
                        else if (u.getProfileType().equals("Teacher")) {
                            //recyclerViewTeacher.setAlpha(1);
                            //initRecyclerViewDataTeachers();
                        }
                    }*/
                }
                else {
                    Log.d("Function getUserInfo", "Response Failed");
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Function getUserInfo", "Failure");
                Log.d("Function getUserInfo Throwable T", t.toString());
            }
        });
    }

}