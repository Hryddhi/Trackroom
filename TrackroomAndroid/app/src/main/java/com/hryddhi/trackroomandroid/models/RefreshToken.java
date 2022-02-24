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


public class RefreshToken {

    @SerializedName("refresh_token")
    @Expose
    private String refresh_token;

    public RefreshToken(String refresh) { this.refresh_token = refresh;}

    public String getRefresh_token() { return refresh_token; }

    public void setRefresh_token(String refresh_token) { this.refresh_token = refresh_token; }

    @Override
    public String toString(){
        return "{refresh_token" +
                "refresh_token='" + refresh_token + '\'' +
                '}';
    }
}
