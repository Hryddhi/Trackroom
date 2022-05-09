package com.rifatul.trackroom.ui.ClassList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rifatul.trackroom.R;
import com.rifatul.trackroom.adapters.RecyclerViewAdapterClassList;
import com.rifatul.trackroom.adapters.RecyclerViewAdapterClassListFree;
import com.rifatul.trackroom.adapters.RecyclerViewAdapterClassListPaid;
import com.rifatul.trackroom.adapters.RecyclerViewAdapterClassListRecommend;
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
    RecyclerView list_recom, list2, list3, list4;
    AppCompatButton btn_add_paid, btn_add_create, btn_add_public;
    List<ItemClass> classList = new ArrayList<>();
    List<ItemClass> classListPaid = new ArrayList<>();
    List<ItemClass> classListFree = new ArrayList<>();



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
        list_recom = view.findViewById(R.id.list_recom);
        list2 = view.findViewById(R.id.list2);
        list3 = view.findViewById(R.id.list3);
        list4 = view.findViewById(R.id.list4);
        btn_add_paid = view.findViewById(R.id.btn_add_private);
        btn_add_create = view.findViewById(R.id.btn_add_create);
        btn_add_public = view.findViewById(R.id.btn_add_public);

        btn_add_paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startJoinClass();
            }
        });

        btn_add_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCreateClass();
            }
        });

        btn_add_public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPublicClass();
            }
        });

        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<ItemClass> data = new ArrayList<>();
        Log.d("Bearer Access on Fragment Class List", getAccess());
        Call<List<ItemClass>> getClassList = getApi().getCreatedClassroomList(getAccess());

        Call<List<ItemClass>> getPrivateClassList = getApi().getPrivateClassroomList(getAccess());

        Call<List<ItemClass>> getPublicClassList = getApi().getPublicClassroomList(getAccess());

        Call<List<ItemClass>> getRecommendClassList = getApi().getRecommendClassroomList(getAccess());

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

        getPrivateClassList.enqueue(new Callback<List<ItemClass>>() {
            @Override
            public void onResponse(Call<List<ItemClass>> call, Response<List<ItemClass>> response) {
                Log.d("TAG", "Response " + response.code());

                if (response.isSuccessful()) {
                    List<ItemClass> data = response.body();
                    for (ItemClass itemClass : data) {
                        classListPaid.add(itemClass);
                        Log.d("Class Title" , itemClass.getTitle());
                        Log.d("Class Type" , itemClass.getClassType());
                    }
                    addDataToRecyclerViewPaid(classListPaid);
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

        getPublicClassList.enqueue(new Callback<List<ItemClass>>() {
            @Override
            public void onResponse(Call<List<ItemClass>> call, Response<List<ItemClass>> response) {
                Log.d("TAG", "Response " + response.code());

                if (response.isSuccessful()) {
                    List<ItemClass> data = response.body();
                    for (ItemClass itemClass : data) {
                        classListFree.add(itemClass);
                        Log.d("Class Title" , itemClass.getTitle());
                        Log.d("Class Type" , itemClass.getClassType());
                    }
                    addDataToRecyclerViewFree(classListFree);
                    Log.d("TAG", "Response " + response.code());
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

        getRecommendClassList.enqueue(new Callback<List<ItemClass>>() {
            @Override
            public void onResponse(Call<List<ItemClass>> call, Response<List<ItemClass>> response) {
                Log.d("TAG", "Response " + response.code());

                if (response.isSuccessful()) {
                    List<ItemClass> data = response.body();
                    for (ItemClass itemClass : data) {
                        classListFree.add(itemClass);
                        Log.d("Class Title" , itemClass.getTitle());
                        Log.d("Class Type" , itemClass.getClassType());
                    }
                    addDataToRecyclerViewRecommend(classListFree);
                    Log.d("TAG", "Response " + response.code());
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
        list2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        list2.setAdapter(new RecyclerViewAdapterClassList(data));

    }

    private void addDataToRecyclerViewPaid (List<ItemClass> data) {
        list3.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        list3.setAdapter(new RecyclerViewAdapterClassListPaid(data));

    }

    private void addDataToRecyclerViewFree (List<ItemClass> data) {
        list4.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        list4.setAdapter(new RecyclerViewAdapterClassListFree(data));

    }

    private void addDataToRecyclerViewRecommend (List<ItemClass> data) {
        list_recom.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        list_recom.setAdapter(new RecyclerViewAdapterClassListRecommend(data));
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