package com.rifatul.trackroom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rifatul.trackroom.adapters.RecyclerViewAdapterClassList;
import com.rifatul.trackroom.adapters.RecyclerViewAdapterClassListPublic;
import com.rifatul.trackroom.models.ItemClass;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPublicClass extends BaseDataActivity {
    RecyclerView list_public;
    EditText class_search;
    RecyclerViewAdapterClassListPublic adapter;
    AppCompatButton btn_add_create_public;
    List<ItemClass> classListPublic = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);
        Log.d("TAG", "onCreate: ");

        list_public = findViewById(R.id.list_public);
        btn_add_create_public = findViewById(R.id.btn_add_create_public);
        class_search = findViewById(R.id.class_search);
        initData();

        class_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //filter(s.toString());

            }
        });



        /*class_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });*/
    }

    /*private void filter(String text) {
        ArrayList<ItemClass> filteredList = new ArrayList<>();
        for(ItemClass item : classListPublic) {
            if(item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);

    }*/



    private void initData() {
        List<ItemClass> data = new ArrayList<>();
        Intent ClassroomInfo = getIntent();

        int classroomPk = ClassroomInfo.getIntExtra("classroomPk", 0);
        Log.d("Bearer Access on Fragment Class List", getAccess());
        Call<List<ItemClass>> getClassroomList = getApi().getClassroomList(getAccess());

        getClassroomList.enqueue(new Callback<List<ItemClass>>() {
            @Override
            public void onResponse(Call<List<ItemClass>> call, Response<List<ItemClass>> response) {
                Log.d("TAG", "Response " + response.code());


                if (response.isSuccessful()) {
                    List<ItemClass> data = response.body();
                    for (ItemClass itemClass : data) {
                        classListPublic.add(itemClass);
                    }
                    addDataToRecyclerViewPublic(classListPublic);
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

    private void addDataToRecyclerViewPublic(List<ItemClass> data) {
        list_public.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        list_public.setAdapter(new RecyclerViewAdapterClassListPublic(data));
    }

    @Override
    public void onBackPressed() {

            Intent back = new Intent(getApplicationContext(), ActivityTrackroom.class);
            startActivity(back);

    }


}
