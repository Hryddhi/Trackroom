package com.hryddhi.trackroom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefreshToken {
    @SerializedName("refresh_token")
    @Expose
    private String refresh_token;

    public RefreshToken(String refresh) {
        this.refresh_token = refresh;
    }

    public String getRefreshToken() {
        return refresh_token;
    }

    public void setRefreshToken(String refresh) {
        this.refresh_token = refresh;
    }

    @Override
    public String toString() {
        return "{refresh_token" +
                "refresh_token='" + refresh_token + '\'' +
                '}';
    }
}
