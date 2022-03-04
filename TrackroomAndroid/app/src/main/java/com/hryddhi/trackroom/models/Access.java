package com.hryddhi.trackroom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Access {
    @SerializedName("access")
    @Expose
    private String access;

    public Access(String access) {
        this.access = access;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    @Override
    public String toString() {
        return "Access{" +
                "access='" + access + '\'' +
                '}';
    }
}
