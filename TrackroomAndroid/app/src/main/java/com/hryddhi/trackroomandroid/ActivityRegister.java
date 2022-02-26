package com.hryddhi.trackroomandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.hryddhi.trackroomandroid.interfaces.ApiInterface;
import com.hryddhi.trackroomandroid.models.Access;
import com.hryddhi.trackroomandroid.models.Login;
import com.hryddhi.trackroomandroid.models.Refresh;
import com.hryddhi.trackroomandroid.models.RefreshToken;
import com.hryddhi.trackroomandroid.models.Register;
import com.hryddhi.trackroomandroid.models.Token;
import com.hryddhi.trackroomandroid.models.User;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityRegister extends BaseDataActivity {
    TextView registerHeading;
    EditText name;
    EditText email;
    EditText password;
    EditText password2;
    Button btnRegister;
    TextView txtSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Getting ids of all the view in the class
        registerHeading = findViewById(R.id.register_heading);
        name = findViewById(R.id.et_name);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        password2 = findViewById(R.id.et_password2);
        btnRegister = findViewById(R.id.btn_register);
        txtSignIn = findViewById(R.id.tv_login);

        // Setting the heading of the register page according to the user
        String message = getIntent().getStringExtra("user");
        if(message == null) {
            registerHeading.setText("Create Account");
        } else {
            registerHeading.setText(message + "Account");
        }

        // OnClickListener for clickable text sign in
        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLogin();
            }
        });

        // OnClickListener for button register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Getting the text in the edit text fields
                Log.d("Checking Register", "onClick: Register");
                String userName = name.getText().toString();
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                String userPassword2 = password2.getText().toString();

                if(checkData(userName, userEmail, userPassword, userPassword2)) {
                    registerUser(userName, userEmail, userPassword, userPassword2);
                } else {
                    Toast.makeText(getApplicationContext(), "Missing Information", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressLint("LongLogTag")
    private boolean checkData(String userName, String userEmail, String userPassword, String userPassword2) {
        if(validateInformation(name) && validateInformation(email) && validateInformation(password) && validateInformation(password2)) {
            Log.d("Checking length validation", "Passed");
            if(validateEmail(userEmail, email)) {
                Log.d("Checking email validation", "Passed");
                if(userPassword.equals(userPassword2)) {
                    Log.d("Checking password validation", "Passed");
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    private void registerUser(String userName, String userEmail, String userPassword, String userPassword2) {
        Log.d("Function", "registerUser");
        Register registerUser = new Register(userName, userEmail, userPassword, userPassword2);
        Call<ResponseBody> callRegister = getApi().register(registerUser);
        callRegister.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("Function", "Callback On Response");
                String responseCode = String.valueOf(response.code());

                if(response.isSuccessful()) {
                    Log.d("Callback On Response", "Response Success");
                    Log.d("Response Code", responseCode);
                    Toast.makeText(ActivityRegister.this, "Successfully Created An Account", Toast.LENGTH_LONG).show();
                    startLogin();
                } else if(response.code() == BAD_REQUEST) {
                    Log.d("Callback On Response", "Account already exists");
                    Log.d("Callback On Response", responseCode);
                    Toast.makeText(getApplicationContext(), "Account already exists with provided email", Toast.LENGTH_LONG).show();
                } else if(response.code() == UNAUTHORIZED) {
                    Log.d("Callback On Response", "Bad Response");
                    Log.d("Callback On Response", responseCode);
                    Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG).show();
            } else {
                    Log.d("Callback On Response", "Bad Response Else");
                    Log.d("Callback On Response", responseCode);
                }
        }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("On Call Failure", t.toString());
                Toast.makeText(getApplicationContext(), "Server Not Found, Make sure you are connected to the internet", Toast.LENGTH_SHORT).show();
            }

            });
    }

    @Override
    public void onBackPressed() {
        if(validateEditText(name) || validateEditText(email) || validateEditText(password) || validateEditText(password2)) {
            showAlert();
        } else {
            startLogin();
        }
    }

    public void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Are you sure you want to go back, unsaved data will be lost?");
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityRegister.super.onBackPressed();
            }
        });
        builder.show();
    }
}
