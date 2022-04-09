package com.rifatul.trackroom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemComments {
    @SerializedName("pk")
    @Expose
    private int pk;

    @SerializedName("title")
    @Expose
    private String title;


    public ItemComments(String title) {
        this.title = title;
    }

    public int getPk() { return pk; }

    public void setPk(int pk) { this.pk = pk; }


    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

}
