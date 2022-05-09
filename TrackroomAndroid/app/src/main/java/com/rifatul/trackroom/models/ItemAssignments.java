package com.rifatul.trackroom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemAssignments {
    @SerializedName("pk")
    @Expose
    private int pk;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("date_created")
    @Expose
    private String date_created;

    @SerializedName("post_type")
    @Expose
    private String post_type;

    @SerializedName("creator_image")
    @Expose
    private String creator_image;


    public ItemAssignments(String title, String description, String date_created, String post_type, String creator_image) {
        this.title = title;
        this.description = description;
        this.date_created = date_created;
        this.post_type = post_type;
        this.creator_image = creator_image;
    }

    public int getPk() { return pk; }

    public void setPk(int pk) { this.pk = pk; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getDate_created() { return date_created; }

    public void setDate_created(String date_created) { this.date_created = date_created; }

    public String getPost_type() { return post_type; }

    public void setPost_type(String post_type) { this.post_type = post_type; }

    public String getCreator_image() { return creator_image; }

    public void setCreator_image(String creator_image) { this.creator_image = creator_image; }
}
