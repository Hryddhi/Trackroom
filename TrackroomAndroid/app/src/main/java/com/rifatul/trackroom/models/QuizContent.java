package com.rifatul.trackroom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizContent {

    @SerializedName("question")
    @Expose
    private String question;

    @SerializedName("option")
    @Expose
    private String[] option;

    @SerializedName("correctAnswer")
    @Expose
    private int correct_answer;

    public QuizContent(String question, String[] option, int correct_answer) {
        this.question = question;
        this.option = option;
        this.correct_answer = correct_answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getOption() {
        return option;
    }

    public void setOption(String[] option) {
        this.option = option;
    }

    public int getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(int correct_answer) {
        this.correct_answer = correct_answer;
    }
}
