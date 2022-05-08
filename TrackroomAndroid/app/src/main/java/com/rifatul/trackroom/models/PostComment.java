package com.rifatul.trackroom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostComment {
    @SerializedName("comment")
    @Expose
    private String comment;

    public PostComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
