package com.hryddhi.trackroomandroid.interfaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hryddhi.trackroomandroid.models.Access;
import com.hryddhi.trackroomandroid.models.Login;
import com.hryddhi.trackroomandroid.models.Refresh;
import com.hryddhi.trackroomandroid.models.RefreshToken;
import com.hryddhi.trackroomandroid.models.Register;
import com.hryddhi.trackroomandroid.models.Token;
import com.hryddhi.trackroomandroid.models.User;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("register")
    Call<ResponseBody> register(@Body Register register);

    @POST("login")
    Call<Token> login(@Body Login login);

    @POST("GoogleSignIn")
    Call<Token> googleSignIn(@Body MultipartBody googleSignInBody);

    @POST("token/refresh")
    Call<Access> refresh(@Body Refresh refresh);

    @GET("account/u/")
    Call<User> account(@Header("Authorization") String token);

    @POST("logout/blacklist")
    Call<ResponseBody> blackListRefresh(@Body RefreshToken refreshToken);
}
