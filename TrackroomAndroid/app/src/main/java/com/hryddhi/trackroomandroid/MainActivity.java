package com.hryddhi.trackroomandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hryddhi.trackroomandroid.interfaces.ApiInterface;
import com.hryddhi.trackroomandroid.models.Access;
import com.hryddhi.trackroomandroid.models.Refresh;
import com.hryddhi.trackroomandroid.models.RefreshToken;
import com.hryddhi.trackroomandroid.models.Token;
import com.hryddhi.trackroomandroid.models.User;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseDataActivity {
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLoggedIn();

        btn_login = (Button) findViewById(R.id.btn_login_main);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLogin();

            }
        });
    }
    @SuppressLint("LongLogTag")
    private void isLoggedIn() {
        Log.d("Function isLoggedIn", "Inside");
        Log.d("Function isLoggedIn access", getAccess());

        if(getRefresh().equals("fail")) {
            deleteToken();
            startLogin();
        }
        else {
            getAccountInfo();
        }
    }

    private void getAccountInfo() {
        Call<User> getUserInfo = getApi().account(getAccess());
        getUserInfo.enqueue(new Callback<User>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    User u = response.body();
                    Log.d("Function ActivityLogin getAccountInfo", "Response Success");
                    saveUser(u);
                    if(u.getIsFirstLogin()) {
                        Log.d("Function ActivityLogin getAccountInfo if isFirstLogin", "Inside");
                        //startUserSelection;
                    }
                    else if(!u.getIsFirstLogin()) {
                        Log.d("Function ActivityLogin getAccountInfo if not isFirstLogin", "Inside");
                        //startTrackroom;
                    }
                    else if(response.code() == UNAUTHORIZED) {
                        Log.d("Function getUserInfo", "Response Unauthorized");
                        getToken(getRefresh());
                    }
                    else {
                        Log.d("Function getUserInfo", "Response Failed");
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Function getUserInfo", "Failure");
                Log.d("Function getUserInfo Throwable T", t.toString());

            }
        });

    }

    public void getToken(String refresh) {
        Log.d("Function getToken", "Inside");
        Refresh refreshToken = new Refresh(refresh);
        Call<Access> accessCall = getApi().refresh(refreshToken);
        accessCall.enqueue(new Callback<Access>() {
            @Override
            public void onResponse(Call<Access> call, Response<Access> response) {
                if(response.isSuccessful()) {
                    Log.d("Function getToken", "Response Success");
                    String access = response.body().getAccess();
                    saveToken(access, refresh);
                    getAccountInfo();
                }
                else {
                    Log.d("Function getToken", "Response Failed");
                    blacklistToken(refresh);
                    startLogin();
                }
            }

            @Override
            public void onFailure(Call<Access> call, Throwable t) {

            }
        });
    }
    @Override
    public void onBackPressed() { finish();}
}