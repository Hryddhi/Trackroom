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
import com.rifatul.trackroom.adapters.RecyclerViewAdapterUnifiedAssignmentList;
import com.rifatul.trackroom.models.ItemAssignments;
import com.rifatul.trackroom.models.ItemNotification;
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

    List<ItemNotification> assignmentList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unified_list, container, false);

        recyclerViewStudent = view.findViewById(R.id.rv_unified_assignment);
        //recyclerViewTeacher = view.findViewById(R.id.rv_unified_questions);


        getAccountInfo();

        return view;

    }

    private void initRecyclerViewDataStudent() {
        Log.d("Bearer Access on Fragment Class List", getAccess());
        Call<List<ItemNotification>> getUnifiedAssignments = getApi().getUnifiedAssignments(getAccess());

        getUnifiedAssignments.enqueue(new Callback<List<ItemNotification>>() {
            @Override
            public void onResponse(Call<List<ItemNotification>> call, Response<List<ItemNotification>> response) {
                Log.d("TAG", "Response " + response.code());

                if (response.isSuccessful()) {
                    List<ItemNotification> data = response.body();
                    /*for (ItemAssignments itemAssignments : data) {
                        assignmentList.add(itemAssignments);
                    }*/
                    assignmentList.addAll(data);

                    addDataToRecyclerView(recyclerViewStudent, assignmentList);
                }
                else
                    Toast.makeText(getContext(), "Failed To Receive Class List", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<ItemNotification>> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.toString());
                Toast.makeText(getContext(), "Server Not Found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addDataToRecyclerView (RecyclerView recyclerView, List<ItemNotification> data) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RecyclerViewAdapterUnifiedAssignmentList(data));
    }

    private void getAccountInfo() {
        Call<User> getUserInfo = getApi().account(getAccess());
        getUserInfo.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User u = response.body();
                    recyclerViewStudent.setAlpha(1);
                    initRecyclerViewDataStudent();
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