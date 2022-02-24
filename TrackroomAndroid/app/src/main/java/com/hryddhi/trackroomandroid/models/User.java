package com.hryddhi.trackroomandroid.models;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("is_first_login")
    @Expose
    private Boolean isFirstLogin;

    @SerializedName("profile_type")
    @Expose
    private String profileType;

    @SerializedName("profile_image")
    @Expose
    private String profileImage;

    public User(String email, String username, Boolean isFirstLogin, String profileType, String profileImage) {
        super();
        this.email = email;
        this.username = username;
        this.isFirstLogin = isFirstLogin;
        this.profileType = profileType;
        this.profileImage = profileImage;
    }

    public String getEmail() { return email;}

    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public Boolean getIsFirstLogin() { return isFirstLogin; }

    public void setFirstLogin(Boolean firstLogin) { isFirstLogin = firstLogin; }

    public String getProfileType() { return profileType; }

    public void setProfileType(String profileType) { this.profileType = profileType; }

    public String getProfileImage() { return profileImage; }

    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }
}
