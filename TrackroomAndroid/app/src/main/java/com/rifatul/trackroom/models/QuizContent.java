package com.rifatul.trackroom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizContent {

    @SerializedName("question")
    @Expose
    private String question;

    @SerializedName("options")
    @Expose
    private String[] options;

    @SerializedName("correct_option")
    @Expose
    private int correct_option;

    public QuizContent(String question, String[] options, int correct_option) {
        this.question = question;
        this.options = options;
        this.correct_option = correct_option;
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

    public int getCorrect_option() {
        return correct_option;
    }

    public void setCorrect_option(int correct_option) {
        this.correct_option = correct_option;
    }
}
