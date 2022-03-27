package com.rifatul.trackroom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
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
    AppCompatButton btn_add_create_public;
    List<ItemClass> classListPublic = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);
        Log.d("TAG", "onCreate: ");

        list_public = findViewById(R.id.list_public);
        btn_add_create_public = findViewById(R.id.btn_add_create_public);
        initData();

       /* btn_add_create_public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: Button");
                //startCourseDetailed();
                //initData();
            }
        });*/


        /*btn_add_create_public.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                initClass();
                Intent ClassroomInfo = getIntent();
                int classroomPk = ClassroomInfo.getIntExtra("classroomPk", 0);
                Call<ResponseBody> joinPublicClass = getApi().joinPublicClass(getAccess(),classroomPk);
                joinPublicClass.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Log.d("Function joinClass", "onResponse Success");
                            Log.d("Function joinClass response code", String.valueOf(response.code()));
                            Log.d("Classroom pk", String.valueOf(classroomPk));
                            Toast.makeText(getApplicationContext(), "Joined Class Successfully", Toast.LENGTH_SHORT).show();
                            startTrackroom();
                        }
                        else {
                            Log.d("Function joinClass", "onResponse Failed");
                            Log.d("Function joinClass response code", String.valueOf(response.code()));
                            Toast.makeText(getApplicationContext(), "Class Join Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("Function joinClass", t.toString());
                        Toast.makeText(getApplicationContext(), "Server Not Found", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });*/
    }

    private void initClass(){
        List<ItemClass> data = new ArrayList<>();
        Intent ClassroomInfo = getIntent();

        int classroomPk = ClassroomInfo.getIntExtra("classroomPk", 0);
        Log.d("Bearer Access on Fragment Class List", getAccess());
        Call<List<ItemClass>> getClassroomPk = getApi().getClassroomPk(getAccess(), classroomPk);

        getClassroomPk.enqueue(new Callback<List<ItemClass>>() {
            @Override
            public void onResponse(Call<List<ItemClass>> call, Response<List<ItemClass>> response) {
                Log.d("TAG", "Response " + response.code());


                if (response.isSuccessful()) {
                    Log.d("Classroom PK", String.valueOf(classroomPk));
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
