package com.rifatul.trackroom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemAssignments {
    @SerializedName("pk")
    @Expose
    private int pk;
    @SerializedName("classroom")
    @Expose
    private String classroom;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("date")
    @Expose
    private String date;

    public ItemAssignments(String classroom, String message, String date) {
        this.classroom = classroom;
        this.description = description;
        this.date = date;
    }


    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String message) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPk() { return pk; }

    public void setPk(int pk) { this.pk = pk; }
}
