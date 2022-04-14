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
    private String correctAnswer;

    public QuizContent(String question, String[] option, String correctAnswer) {
        this.question = question;
        this.option = option;
        this.correctAnswer = correctAnswer;
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

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
