package com.rifatul.trackroom.ui.ClassList;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rifatul.trackroom.R;
import com.rifatul.trackroom.adapters.RecyclerViewAdapterClassList;
import com.rifatul.trackroom.models.ItemClass;
import com.rifatul.trackroom.models.User;
import com.rifatul.trackroom.ui.BaseDataFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentClassList extends BaseDataFragment {

    public FragmentClassList(){}
    RecyclerView recyclerView;
    List<ItemClass> classList = new ArrayList<>();


    public static FragmentClassList newInstance() {
        return new FragmentClassList();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_class, container, false);

        //All find view by IDs
        recyclerView = view.findViewById(R.id.list);
        /*FloatingActionButton addFab = view.findViewById(R.id.add_fab);

        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAccountInfo();
            }
        });*/

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //List<ItemClass> data = new ArrayList<>();
        Log.d("Bearer Access on Fragment Class List", getAccess());
        Call<List<ItemClass>> getClassList = getApi().getClassroomList(getAccess());

        getClassList.enqueue(new Callback<List<ItemClass>>() {
            @Override
            public void onResponse(Call<List<ItemClass>> call, Response<List<ItemClass>> response) {
                Log.d("TAG", "Response " + response.code());

                if (response.isSuccessful()) {
                    List<ItemClass> data = response.body();
                    for (ItemClass itemClass : data) {
                        classList.add(itemClass);
                    }
                    addDataToRecyclerView(classList);
                }
                else
                    Toast.makeText(getContext(), "Failed To Receive Class List", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<ItemClass>> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.toString());
                Toast.makeText(getContext(), "Server Not Found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addDataToRecyclerView (List<ItemClass> data) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RecyclerViewAdapterClassList(data));
    }

   private void getAccountInfo() {
        Call<User> getUserInfo = getApi().account(getAccess());

        getUserInfo.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User u = response.body();
                    //saveUser(u);
                    Log.d("Function ActivityLogin getAccountInfo", "Response Success");
                    //Log.d("Function ActivityLogin getAccountInfo", u.getProfileType());
                    //saveUser(u);

                    /*if (u.getProfileType() == null ) {
                        Toast.makeText(getContext(), "Profile Type Not Set.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (u.getProfileType().equals("Teacher")) {
                            startCreateClass();
                        }
                        else if (u.getProfileType().equals("Student")) {
                            startJoinClass();
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