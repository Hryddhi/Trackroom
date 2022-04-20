package com.rifatul.trackroom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.ArrayList;

public class QuizData {
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("start_time")
    @Expose
    private String start_time;

    @SerializedName("end_time")
    @Expose
    private String end_time;

    @SerializedName("questions")
    @Expose
    private ArrayList<QuizContent> questions;

    public QuizData(String title, String description, String start_time, String end_time, ArrayList<QuizContent> questions) {
        this.title = title;
        this.description = description;
        this.start_time = start_time;
        this.end_time = end_time;
        this.questions = questions;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public ArrayList<QuizContent> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<QuizContent> questions) {
        this.questions = questions;
    }
}
