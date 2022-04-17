package com.rifatul.trackroom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemNotification {
    @SerializedName("pk")
    @Expose
    private int pk;

    @SerializedName("classroom")
    @Expose
    private String classroom;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("date_created")
    @Expose
    private String date_created;



    public ItemNotification(String classroom, String message, String date_created) {
        this.classroom = classroom;
        this.message = message;
        this.date_created = date_created;
    }

    public int getPk() { return pk; }

    public void setPk(int pk) { this.pk = pk; }

    public String getClassroom() { return classroom; }

    public void setClassroom(String classroom) { this.classroom = classroom; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public String getDate_created() { return date_created; }

    public void setDate_created(String date_created) { this.date_created = date_created; }

}
