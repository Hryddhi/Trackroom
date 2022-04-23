package com.rifatul.trackroom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Answers {
    @SerializedName("answers")
    @Expose
    ArrayList<QuizAnswer> answers;

    public Answers(ArrayList<QuizAnswer> answers) {
        this.answers = answers;
    }

    public ArrayList<QuizAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<QuizAnswer> answers) {
        this.answers = answers;
    }
}
