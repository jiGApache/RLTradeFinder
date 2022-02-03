package com.chekhurov.rltradefinder;

import androidx.annotation.Nullable;

public class RLItem {

    private String name;

    @Nullable
    private String color;
//    private String certification;
//    private String image;
//    private int minPrice;
//    private int maxPrice;
//    private boolean isBlueprint;

    public RLItem(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "RLItem{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
