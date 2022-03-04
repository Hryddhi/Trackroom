package com.hryddhi.trackroom;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.hryddhi.trackroom.models.Access;
import com.hryddhi.trackroom.models.Refresh;
import com.hryddhi.trackroom.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseDataActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLoggedIn();
    }
    @SuppressLint("LongLogTag")
    private void isLoggedIn() {
        Log.d("Function isLoggedIn", "Inside");
        Log.d("Function isLoggedIn access", getAccess());

        if (getRefresh().equals("fail")) {
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
                if (response.isSuccessful()) {
                    User u = response.body();
                    Log.d("Function ActivityLogin getAccountInfo", "Response Success");
                    /*if (u.getIsFirstLogin()){
                        Log.d("Function ActivityLogin getAccountInfo", "profile type is first login");
                    }
                    if (!u.getIsFirstLogin()){
                        Log.d("Function ActivityLogin getAccountInfo", "profile type is first login is set");
                    }*/
                    saveUser(u);
                    /*if (u.getIsFirstLogin()) {
                        Log.d("Function ActivityLogin getAccountInfo if isFirstLogin", "inside");
                        startUserSelection();
                    }

                     else*/ if (!u.getIsFirstLogin()) {
                        Log.d("Function ActivityLogin getAccountInfo if not isFirstLogin", "inside");
                        startTrackroom();
                    }
                }
                else if(response.code() == UNAUTHORIZED) {
                    Log.d("Function getUserInfo", "Response Unauthorized");
                    getToken(getRefresh());
                }
                else {
                    Log.d("Function getUserInfo", "Response Failed");
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
    public void onBackPressed(){
        finish();
    }
}