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
import com.hryddhi.trackroomandroid.models.ItemClass;
import com.hryddhi.trackroomandroid.models.Login;
import com.hryddhi.trackroomandroid.models.Refresh;
import com.hryddhi.trackroomandroid.models.RefreshToken;
import com.hryddhi.trackroomandroid.models.Register;
import com.hryddhi.trackroomandroid.models.Token;
import com.hryddhi.trackroomandroid.models.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {

    @POST("register")
    Call<ResponseBody> register(@Body Register register);

    @POST("login")
    Call<Token> login(@Body Login login);

    @POST("GoogleSignIn")
    Call<Token> googleSignIn(@Body MultipartBody googleSignInBody);

    @GET("account/u/")
    Call<User> account(@Header("Authorization") String token);

    @PUT("account/u/configure-profile/")
    Call<ResponseBody> profileConfigure(@Header("Authorization") String authorization,
                                        @Body MultipartBody multipartBody);

    @PATCH("account/u/")
    Call<ResponseBody> updateAccount(@Header("Authorization") String token,
                                     @Body MultipartBody multipartBody);


    @POST("token/refresh")
    Call<Access> refresh(@Body Refresh refresh);

    @POST("logout/blacklist")
    Call<ResponseBody> blackListRefresh(@Body RefreshToken refreshToken);
}
