package com.rifatul.trackroom.models;

import java.util.ArrayList;

public class VerticalClass {
    String title;
    ArrayList<HorizontalClass> arrayList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<HorizontalClass> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<HorizontalClass> arrayList) {
        this.arrayList = arrayList;
    }
}
