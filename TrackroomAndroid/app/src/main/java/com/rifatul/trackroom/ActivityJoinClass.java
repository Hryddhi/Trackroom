package com.rifatul.trackroom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;

import com.rifatul.trackroom.models.ClassCode;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityJoinClass extends BaseDataActivity{
    EditText etJoinClassCode;
    AppCompatButton btnJoinClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_paid);

        etJoinClassCode = findViewById(R.id.class_code);
        btnJoinClass = findViewById(R.id.btn_join);

        btnJoinClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEditText(etJoinClassCode))
                    joinClass();
            }
        });
    }

    private void joinClass() {

        ClassCode classCode = new ClassCode(etJoinClassCode.getText().toString());
        Call<ResponseBody> joinClass = getApi().joinClass(getAccess(),classCode);
        joinClass.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("Function joinClass", "onResponse Success");
                    Log.d("Function joinClass response code", String.valueOf(response.code()));
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

    @Override
    public void onBackPressed() {
        if(validateEditText(etJoinClassCode))
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
                ActivityJoinClass.super.onBackPressed();
            }
        });
        builder.show();
    }
}
