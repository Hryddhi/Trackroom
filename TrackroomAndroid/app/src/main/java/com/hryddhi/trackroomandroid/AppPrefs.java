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

public class AppPrefs {

    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int ACCEPTED = 202;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    private static final int HTTP_TIMEOUT = 5;
    private static final String TAG = "AppPrefs";
    private static final String TOKEN_ACCESS = "TOKEN";
    private static final String TOKEN_REFRESH = "REFRESH";
    private static final String TOKEN_PK = "PK";
    private static final String USER_PK = "PK";
    private static final String USER_NAME = "NAME";
    private static final String USER_EMAIL = "EMAIL";
    private static final String USER_FIRST_LOGIN = "IS_FIRST_LOGIN";
    private static final String USER_PROFILE_TYPE = "PROFILE_TYPE";
    private static final String USER_PROFILE_PICTURE = "PROFILE_PICTURE";
    private static final String BASE_URL = "";
    User usr;

    private static AppPrefs mAppPref;
    private static SharedPreferences sharedpreferences;
    private Context context;
    private ApiInterface apiInterface;

    public AppPrefs(Context context) { this.context = context;}

    public static synchronized AppPrefs getInstance(Context context) {
        if(mAppPref == null) {
            mAppPref = new AppPrefs(context);
        }
        return mAppPref;
    }

    public ApiInterface getApi() {
        if(apiInterface == null) createApi();
        return apiInterface;
    }

    private void createApi() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS)
                .build();

        apiInterface = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(ApiInterface.class);
    }

    public void saveToken(String access, String refresh) {
        Log.d("Function saveToken", "Inside");
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(TOKEN_REFRESH, refresh);
        editor.putString(TOKEN_ACCESS, access);
        editor.apply();
    }

    public Token retrieveToken() {
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String refresh = sharedpreferences.getString(TOKEN_REFRESH, "fail");
        String access = sharedpreferences.getString(TOKEN_ACCESS, "fail");
        Token token = new Token(refresh, access);
        return token;
    }

    public void deleteToken() {
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedpreferences.edit().remove(TOKEN_REFRESH).commit();
        sharedpreferences.edit().remove(TOKEN_ACCESS).commit();
    }

    @SuppressLint("LongLogTag")
    public void saveUser(User user) {
        Log.d("Function saveUser", "Inside");
        Log.d("Function saveUser username", user.getUsername());
        Log.d("Function saveUser email", user.getEmail());
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedpreferences = context.getSharedPreferences(TAG, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(USER_NAME, user.getUsername());
        editor.putString(USER_EMAIL, user.getEmail());
        editor.putString(USER_FIRST_LOGIN, user.getIsFirstLogin().toString());
        editor.putString(USER_PROFILE_TYPE, user.getProfileType());
        editor.putString(USER_PROFILE_PICTURE, null);
        editor.apply();
    }

    @SuppressLint("LongLogTag")
    public User retrieveUser() {
        Log.d("Function saveUser", "Inside");
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = sharedpreferences.getString(USER_NAME, "fail");
        String email = sharedpreferences.getString(USER_EMAIL, "fail");
        String firstLogin = sharedpreferences.getString(USER_FIRST_LOGIN, "false");
        String profileType = sharedpreferences.getString(USER_PROFILE_TYPE, "fail");
        String profilePicture = sharedpreferences.getString(USER_PROFILE_PICTURE, null);

        Log.d("Function retrieveUser", "Inside");
        Log.d("Function retrieveUser username", name);
        Log.d("Function retrieveUser email", email);
        Log.d("Function retrieveUser isFirstLogin", firstLogin);

        User user = new User(email, name, Boolean.valueOf(firstLogin), profileType, profilePicture);
        return user;

    }

    public void clearAllData() {
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void getUserInfo(String refresh, String access) {
        Log.d("Function getUserInfo", "Inside");
        String token = "Bearer " + access;
        Call<User> getUserInfo = getApi().account(token);

        getUserInfo.enqueue(new Callback<User>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    User u = response.body();
                    Log.d("Function getUserInfo", "Response Success");
                    Log.d("Function getUserInfo username", u.getUsername());
                    Log.d("Function getUserInfo email", u.getEmail());
                    Log.d("Function getUserInfo isFirstLogin", u.getIsFirstLogin().toString());
                    Log.d("Function getUserInfo", "Calling saveUser");
                    saveUser(u);
                }
                else if(response.code() == UNAUTHORIZED) {
                    Log.d("Function getUserInfo", "Response Unauthorized");
                    getToken(refresh);
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
                    getUserInfo(access, refresh);
                }
                else {
                    Log.d("Function getToken", "Response Failed");
                    blacklistToken(refresh);
                }
            }

            @Override
            public void onFailure(Call<Access> call, Throwable t) {

            }
        });
    }

    @SuppressLint("LongLogTag")
    public void blacklistToken(String refresh) {
        Log.d("Function blacklistRefresh", "Inside");
        RefreshToken r = new RefreshToken(refresh);
        Call<ResponseBody> blacklistToken = getApi().blackListRefresh(r);
        blacklistToken.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    Log.d("Function blacklistToken", "Response Success");
                    clearAllData();
                    Intent startLogin = new Intent(context, ActivityLogin.class);
                    startLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(startLogin);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Function blacklistToken", "Response Failed");
                Log.d("Function Response", t.toString());
                clearAllData();
                Intent startLogin = new Intent(context, ActivityLogin.class);
                startLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(startLogin);
            }
        });
    }

    public String getAccess() {
        Token token = retrieveToken();
        return "Bearer " + token.getAccess();
    }

    public String getRefresh() {
        Token token = retrieveToken();
        return token.getRefresh();
    }
}
