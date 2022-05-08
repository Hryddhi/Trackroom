package com.rifatul.trackroom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemComments {
    @SerializedName("pk")
    @Expose
    private int pk;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("date_created")
    @Expose
    private String date_created;

    @SerializedName("creator")
    @Expose
    private String creator;

    @SerializedName("creator_image")
    @Expose
    private String creator_image;

    public ItemComments(int pk, String comment, String date_created, String creator, String creator_image) {
        this.pk = pk;
        this.comment = comment;
        this.date_created = date_created;
        this.creator = creator;
        this.creator_image = creator_image;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreator_image() {
        return creator_image;
    }

    public void setCreator_image(String creator_image) {
        this.creator_image = creator_image;
    }
}
