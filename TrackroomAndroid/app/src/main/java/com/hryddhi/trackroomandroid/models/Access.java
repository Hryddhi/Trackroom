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

public class Access {

    @SerializedName("access")
    @Expose
    private String access;

    public Access(String access) { this.access = access;}

    public String getAccess() { return access;}

    public void setAccess(String access) { this.access = access;}

    @Override
    public String toString() {
        return "Access{" +
                "access='" + access + '\'' +
                '}';
    }
}
