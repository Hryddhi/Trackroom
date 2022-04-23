package com.rifatul.trackroom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizStatsTeacher {
    @SerializedName("subscriber")
    @Expose
    private String subscriber;

    @SerializedName("has_attended")
    @Expose
    private boolean has_attended;

    @SerializedName("grade")
    @Expose
    private String grade;

    public QuizStatsTeacher(String subscriber, boolean has_attended, String grade) {
        this.subscriber = subscriber;
        this.has_attended = has_attended;
        this.grade = grade;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public boolean isHas_attended() {
        return has_attended;
    }

    public void setHas_attended(boolean has_attended) {
        this.has_attended = has_attended;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
