package com.rifatul.trackroom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;

import com.rifatul.trackroom.models.CreateClass;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCreateClass extends BaseDataActivity {
    String[] items_type = {"Public", "Private"};
    String[] items_category = {"Calculus", "Quantum Physics", "English Literature", "Machine Learning",
                                "Cooking", "Web Development", "Others"};
    String global_type, global_category;
    EditText etClassroomName, etClassroomDescription;
    AppCompatButton btnCreateClassroom;

    AutoCompleteTextView autoCompleteTextType, autoCompleteTextCategory;
    ArrayAdapter<String> adapterItemsType, adapterItemsCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);

        autoCompleteTextType = findViewById(R.id.auto_complete_txt);
        adapterItemsType = new ArrayAdapter<String>(this, R.layout.list_item_type, items_type);
        autoCompleteTextType.setAdapter(adapterItemsType);
        autoCompleteTextType.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemType = parent.getItemAtPosition(position).toString();
                global_type = itemType;
                Log.d("Class Type:", itemType);

            }
        });

        autoCompleteTextCategory = findViewById(R.id.auto_complete_txt_category);
        adapterItemsCategory = new ArrayAdapter<String>(this, R.layout.list_item_type, items_category);
        autoCompleteTextCategory.setAdapter(adapterItemsCategory);
        autoCompleteTextCategory.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemCategory = parent.getItemAtPosition(position).toString();
                global_category = itemCategory;
                Log.d("Class Category:", itemCategory);
            }
        });

        etClassroomName = findViewById(R.id.class_create_name);
        etClassroomDescription = findViewById(R.id.class_create_description);
        btnCreateClassroom = findViewById(R.id.btn_create_class);

        btnCreateClassroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEditText(etClassroomName))
                    createClass();
            }
        });
    }



    private void createClass() {

        String classTitle = etClassroomName.getText().toString();
        String classDescription = etClassroomDescription.getText().toString();
        String classType = global_type;
        String classCategory = global_category;



        CreateClass createClassInfo = new CreateClass(classTitle, classDescription, classType, classCategory);

        Call<ResponseBody> createClass = getApi().createClass(getAccess(), createClassInfo);

        createClass.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("Function createClass", "onResponse Success");
                    Log.d("Function createClass response code", String.valueOf(response.code()));
                    Toast.makeText(getApplicationContext(), "Class Created Successfully", Toast.LENGTH_SHORT).show();
                    startTrackroom();
                }
                else {
                    Log.d("Function createClass", "onResponse Failed");
                    Log.d("Function createClass response code", String.valueOf(response.code()));
                    Toast.makeText(getApplicationContext(), "Classroom Not Created", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Function createClass", t.toString());
                Toast.makeText(getApplicationContext(), "Server Not Found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(validateEditText(etClassroomName))
            showAlert();
        else {
            Intent back = new Intent(getApplicationContext(), ActivityTrackroom.class);
            startActivity(back);
        }
    }

    public void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Are you sure you want to go back, unsaved will be lost?");
        //builder.setMessage("All unsaved data will be discarded off.");
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ActivityCreateClass.super.onBackPressed();
            }
        });
        builder.show();
    }
}
