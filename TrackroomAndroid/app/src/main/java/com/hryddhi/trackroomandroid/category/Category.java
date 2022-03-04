package com.hryddhi.trackroomandroid.category;

import com.hryddhi.trackroomandroid.models.ItemClass;

import java.util.List;

public class Category {
    private String nameCategory;
    private List<ItemClass> itemClass;

    public Category(String nameCategory, List<ItemClass> itemClass) {
        this.nameCategory = nameCategory;
        this.itemClass = itemClass;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public List<ItemClass> getItemClass() {
        return itemClass;
    }

    public void setItemClass(List<ItemClass> itemClass) {
        this.itemClass = itemClass;
    }

}
