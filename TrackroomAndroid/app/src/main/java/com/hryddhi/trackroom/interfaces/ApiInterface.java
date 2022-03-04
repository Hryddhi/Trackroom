package com.hryddhi.trackroom.interfaces;

import com.hryddhi.trackroom.models.Access;
import com.hryddhi.trackroom.models.Login;
import com.hryddhi.trackroom.models.Refresh;
import com.hryddhi.trackroom.models.RefreshToken;
import com.hryddhi.trackroom.models.Register;
import com.hryddhi.trackroom.models.Token;
import com.hryddhi.trackroom.models.User;

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
import retrofit2.http.Path;

public interface ApiInterface {
    @POST("register")
    Call<ResponseBody> register(@Body Register register);

    @POST("login")
    Call<Token> login(@Body Login login);

    @POST("GoogleSignIn")
    Call<Token> googleSignIn(@Body MultipartBody googleSignInBody);

    @GET("account/u/")
    Call<User> account(@Header("Authorization") String token);

    /*@PUT("account/u/configure-profile/")
    Call<ResponseBody> profileConfigure(@Header("Authorization") String authorization,
                                        @Body MultipartBody multipartBody);

   @PATCH("account/u/")
    Call<ResponseBody> updateAccount(@Header("Authorization") String token,
                                     @Body MultipartBody multipartBody);*/


   /* @PUT("account/u/change-password/")
    Call<ResponseBody> changePassword(@Header("Authorization") String authorization,
                                      @Body ChangePassword changePassword);*/

    @POST("token/refresh")
    Call<Access> refresh(@Body Refresh refresh);

    @POST("logout/blacklist")
    Call<ResponseBody> blackListRefresh(@Body RefreshToken refreshToken);

   /* @GET("classroom/")
    Call<List<ItemClass>> getClassroomList(@Header("Authorization") String token);

    @POST("classroom/")
    Call<ResponseBody> joinClass(@Header("Authorization") String token,
                                 @Body ClassCode classCode);

    @POST("classroom/")
    Call<ResponseBody> createClass(@Header("Authorization") String token,
                                   @Body CreateClass createClass);

    @POST("classroom/{pk}/invite-students/")
    Call<ResponseBody> inviteStudents(@Header("Authorization") String token,
                                      @Path("pk") int pk,
                                      @Body InviteEmail inviteEmail);

    @GET("classroom/{pk}/assignment/")
    Call<List<ItemAssignments>> getAssignmentList(@Header("Authorization") String token,
                                                  @Path("pk") int pk);

    @GET("assignment")
    Call<List<ItemAssignments>> getUnifiedAssignments(@Header("Authorization") String token);

    @POST("classroom/{pk}/assignment/")
    Call<ResponseBody> uploadReadingMaterial(@Header("Authorization") String token,
                                             @Path("pk") int pk,
                                             @Body MultipartBody multipartBody);*/
}
