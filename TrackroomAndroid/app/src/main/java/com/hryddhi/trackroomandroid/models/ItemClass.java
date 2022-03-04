package com.hryddhi.trackroomandroid.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemClass {
        @SerializedName("pk")
        @Expose
        private int pk;
        @SerializedName("teacher")
        @Expose
        private String teacher;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("code")
        @Expose
        private String code;

        public ItemClass() {
        }

        public ItemClass(int pk, String teacher, String title, String description, String code) {
            this.pk = pk;
            this.teacher = teacher;
            this.title = title;
            this.description = description;
            this.code = code;
        }

        public int getPk() {
            return pk;
        }

        public void setPk(int pk) {
            this.pk = pk;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
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

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
}
