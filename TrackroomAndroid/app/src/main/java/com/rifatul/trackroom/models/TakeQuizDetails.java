package com.rifatul.trackroom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TakeQuizDetails {
    @SerializedName("pk")
    @Expose
    private int pk;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("start_time")
    @Expose
    private String start_time;

    @SerializedName("end_time")
    @Expose
    private String end_time;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("date_created")
    @Expose
    private String date_created;

    public TakeQuizDetails(int pk, String title, String start_time, String end_time, String description, String date_created) {
        this.pk = pk;
        this.title = title;
        this.start_time = start_time;
        this.end_time = end_time;
        this.description = description;
        this.date_created = date_created;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }
}
