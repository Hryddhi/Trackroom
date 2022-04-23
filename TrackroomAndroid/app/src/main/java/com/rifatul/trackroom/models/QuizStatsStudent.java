package com.rifatul.trackroom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizStatsStudent {
    @SerializedName("has_attended")
    @Expose
    private boolean has_attended;

    @SerializedName("grade")
    @Expose
    private String grade;

    public QuizStatsStudent(boolean has_attended, String grade) {
        this.has_attended = has_attended;
        this.grade = grade;
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
