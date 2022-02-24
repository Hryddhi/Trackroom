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

public class Refresh {

    @SerializedName("refresh")
    @Expose
    private String refresh;

    public Refresh(String refresh) { this.refresh = refresh;}

    public String getRefresh() { return refresh; }

    public void setRefresh(String refresh) { this.refresh = refresh; }
}
