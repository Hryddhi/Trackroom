package com.rifatul.trackroom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TakeQuizQuestions {
    @SerializedName("pk")
    @Expose
    private int pk;

    @SerializedName("question")
    @Expose
    private String question;

    @SerializedName("options")
    @Expose
    private String[] options;

    public TakeQuizQuestions(int pk, String question, String[] options) {
        this.pk = pk;
        this.question = question;
        this.options = options;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }
}
