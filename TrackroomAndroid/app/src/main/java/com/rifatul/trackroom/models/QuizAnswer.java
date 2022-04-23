package com.rifatul.trackroom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizAnswer {
    @SerializedName("pk")
    @Expose
    private int pk;

    @SerializedName("selected_option")
    @Expose
    private int selected_option;

    public QuizAnswer(int pk, int selected_option) {
        this.pk = pk;
        this.selected_option = selected_option;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public int getSelected_option() {
        return selected_option;
    }

    public void setSelected_option(int selected_option) {
        this.selected_option = selected_option;
    }
}
